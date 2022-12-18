package com.leslie.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.clients.ProductClient;
import com.leslie.clients.UserClient;
import com.leslie.constants.MqConstants;
import com.leslie.order.utils.OrderIdBuilder;
import com.leslie.pojo.Address;
import com.leslie.pojo.Order;
import com.leslie.order.service.OrderService;
import com.leslie.order.mapper.OrderMapper;
import com.leslie.pojo.Product;
import com.leslie.to.OrderToProduct;
import com.leslie.utils.Result;
import com.leslie.vo.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author 20110
 * @description 针对表【tb_order(订单表)】的数据库操作Service实现
 * @createDate 2022-12-12 22:52:06
 */
@CacheConfig(cacheNames = "order")
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

    @Resource
    private ProductClient productClient;

    @Resource
    private UserClient userClient;

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
    @CacheEvict(allEntries = true)
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
            order.setAddressId(orderFromCartParam.getAddressId());
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

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result cancel(String orderId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        Order order = orderMapper.selectOne(queryWrapper);
        //将订单状态设置为已取消
        order.setOrderStatus(4);
        orderMapper.update(order, queryWrapper);

        //准备数据
        OrderToProduct orderToProduct = new OrderToProduct();
        orderToProduct.setProductId(order.getProductId());
        orderToProduct.setNum(order.getProductNum());

        redisTemplate.delete("orderId:" + orderId);

        //发送商品服务消息，通知恢复库存和销量
        try {
            rabbitTemplate.convertAndSend(MqConstants.ORDER_EXCHANGE, MqConstants.ORDER_UPDATE_KEY, orderToProduct);
            return Result.ok("成功取消订单", null);
        } catch (RuntimeException e) {
            log.error("系统异常!\n" + e.getMessage());
            return Result.fail("系统异常! 订单取消失败");
        }

    }

    @CacheEvict(allEntries = true)
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

    @Cacheable(key = "'order:detail:'+#orderId")
    @Override
    public Result one(String orderId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        Order order = orderMapper.selectOne(queryWrapper);

        Address address = userClient.queryByAddressId(order.getAddressId());
        Product product = productClient.cartProductDetail(order.getProductId());

        String addressStr = address.getLinkman() + "，" + address.getPhone() + "，" + address.getAddress();

        //结果封装
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        orderVo.setProductName(product.getProductName());
        orderVo.setProductImage(product.getImageUrl());
        orderVo.setAddress(addressStr);
        return Result.ok(orderVo);
    }

    /**
     * @param userId 用户id
     * @return
     */
    @Cacheable(key = "'order:all:'+#userId")
    @Override
    public Result show(Long userId) {

        //查询用户对应的全部订单数据
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Order> orderList = orderMapper.selectList(queryWrapper);

        Set<Long> productIds = new HashSet<>();
        for (Order order : orderList) {
            productIds.add(order.getProductId());
        }

        //商品数据
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(new ArrayList<>(productIds));
        List<Product> productList = productClient.ids(productIdsParam);

        Map<Long, Product> productMap = productList.stream().
                collect(Collectors.toMap(Product::getProductId, product -> product));

        //返回Vo数据封装
        List<OrderVo> result = new ArrayList<>();

        for (Order order : orderList) {
            Product product = productMap.get(order.getProductId());
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(order, orderVo);
            orderVo.setProductName(product.getProductName());
            orderVo.setProductImage(product.getImageUrl());
            result.add(orderVo);
        }

        return Result.ok(result, result.size());
    }

    @Cacheable(key = "'order:'+#root.methodName+':'+#p0+#p1")
    @Override
    public Result queryPage(Integer curPage, Integer size) {
        //分页传
        Page<Order> page = new Page<>(curPage, size);
        page = orderMapper.selectPage(page, null);
        List<Order> orderList = page.getRecords();

        Set<Integer> addressIds = new HashSet<>();
        Set<Long> productIds = new HashSet<>();

        for (Order order : orderList) {
            addressIds.add(order.getAddressId());
            productIds.add(order.getProductId());
        }

        //地址数据
        AddressIdsParam addressIdsParam = new AddressIdsParam();
        addressIdsParam.setAddressIds(new ArrayList<>(addressIds));
        List<Address> addressList = userClient.ids(addressIdsParam);
        Map<Integer, String> addressMap = addressList.stream().collect(Collectors.toMap(Address::getId, Address::getLinkman));

        //商品数据
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(new ArrayList<>(productIds));
        List<Product> productList = productClient.ids(productIdsParam);
        Map<Long, String> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, Product::getProductName));

        //封装返回结果数据
        List<AdminOrderVo> adminOrderVoList = new ArrayList<>();
        for (Order order : orderList) {
            AdminOrderVo adminOrderVo = new AdminOrderVo(order);
            adminOrderVo.setBuyerName(addressMap.get(order.getAddressId()));
            adminOrderVo.setProductName(productMap.get(order.getProductId()));
            adminOrderVoList.add(adminOrderVo);
        }

        long total = page.getTotal();
        return Result.ok(adminOrderVoList, total);
    }

}




