package com.leslie.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.constants.MqConstants;
import com.leslie.order.utils.OrderIdBuilder;
import com.leslie.pojo.Order;
import com.leslie.order.service.OrderService;
import com.leslie.order.mapper.OrderMapper;
import com.leslie.to.OrderToProduct;
import com.leslie.utils.Result;
import com.leslie.vo.CartVo;
import com.leslie.vo.OrderFromCartParam;
import com.leslie.vo.OrderStatusParam;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author 20110
 * @description 针对表【tb_order(订单表)】的数据库操作Service实现
 * @createDate 2022-12-12 22:52:06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {


    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private OrderIdBuilder orderIdBuilder;


    /**
     * 从购物车生成订单，并保存订单数据业务
     * 1. 将购物车数据转成订单数据
     * 2. 将订单数据批量插入
     * 3. 发送商品服务修改库存消息
     * 4. 发送购物车服务修改消息
     *
     * @param orderFromCartParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addFromCart(OrderFromCartParam orderFromCartParam) {

        //准备数据
        List<Long> cartIds = new ArrayList<>();
        List<OrderToProduct> orderToProducts = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();

        //生成数据
        Long userId = orderFromCartParam.getUserId();


        for (CartVo cartVo : orderFromCartParam.getProducts()) {
            //保存删除的购物车下的id
            cartIds.add(cartVo.getId());

            //商品服务修改的数据
            OrderToProduct orderToProduct = new OrderToProduct();
            orderToProduct.setProductId(cartVo.getProductId());
            orderToProduct.setNum(cartVo.getNum());
            orderToProducts.add(orderToProduct);

            //生成订单编号
            long id = orderIdBuilder.nextId("order");
            String orderId = String.valueOf(id);

            Order order = new Order();
            order.setOrderId(orderId);
            order.setUserId(userId);
            order.setProductId(cartVo.getProductId());
            order.setProductNum(cartVo.getNum());
            BigDecimal totalPrice = cartVo.getPrice().multiply(new BigDecimal(cartVo.getNum()));
            order.setProductPrice(totalPrice);
            orderList.add(order);

            //并设置订单过期时间为半小时
            redisTemplate.opsForValue().set("orderId:" + orderId,
                    orderFromCartParam.getUserId() + ":" + orderFromCartParam.getProducts(),
                    3600, TimeUnit.SECONDS);
        }

        //批量保存订单数据
        boolean isAdd = saveBatch(orderList);
        if (!isAdd) {
            return Result.fail("系统异常，生成订单失败！");
        }

        //发送购物车服务消息
        rabbitTemplate.convertAndSend(MqConstants.ORDER_EXCHANGE, MqConstants.CART_CLEAR_KEY, cartIds);
        //发送商品服务消息
        rabbitTemplate.convertAndSend(MqConstants.ORDER_EXCHANGE, MqConstants.ORDER_UPDATE_KEY, orderToProducts);
        return Result.ok();
    }

    @Override
    public Result cancel(String orderId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        Order order = orderMapper.selectOne(queryWrapper);
        //将订单状态设置为已取消
        order.setOrderStatus(4);

        //准备数据
        OrderToProduct orderToProduct = new OrderToProduct();
        orderToProduct.setProductId(order.getProductId());
        orderToProduct.setNum(order.getProductNum());
        //发送商品服务消息，通知恢复库存和销量
        try {
            rabbitTemplate.convertAndSend(MqConstants.ORDER_EXCHANGE, MqConstants.ORDER_UPDATE_KEY, orderToProduct);
            return Result.ok("成功取消订单", null);
        } catch (RuntimeException e) {
            log.error("系统异常!\n" + e.getMessage());
            return Result.fail("系统异常! 订单取消失败");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateOrderStatus(OrderStatusParam orderStatusParam) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderStatusParam.getOrderId());
        Order order = orderMapper.selectOne(queryWrapper);
        order.setOrderStatus(orderStatusParam.getOrderStatus());
        int row = orderMapper.update(order, queryWrapper);
        return row;
    }

}




