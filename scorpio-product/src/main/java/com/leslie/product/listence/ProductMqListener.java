package com.leslie.product.listence;


import com.leslie.constants.MqConstants;
import com.leslie.product.service.ProductService;
import com.leslie.to.OrderToProduct;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @descript 商品的mq消息监听
 */
@Component
public class ProductMqListener {

    @Resource
    private ProductService productService;

    /**
     * 监听订单服务修改购物车数据的业务
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstants.ORDER_UPDATE_QUEUE),
            exchange = @Exchange(MqConstants.ORDER_EXCHANGE),
            key = MqConstants.ORDER_UPDATE_KEY)
    )
    public void pubAddOrder(List<OrderToProduct> orderToProducts) {
        productService.pubAddOrder(orderToProducts);
    }

    /**
     * 监听订单服务取消的业务
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstants.ORDER_UPDATE_QUEUE),
            exchange = @Exchange(MqConstants.ORDER_EXCHANGE),
            key = MqConstants.ORDER_UPDATE_KEY)
    )
    public void pubCancelOrder(OrderToProduct orderToProduct) {
        productService.pubCancelOrder(orderToProduct);
    }
}
