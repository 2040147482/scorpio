package com.leslie.cart.listence;

import com.leslie.cart.service.CartService;
import com.leslie.constants.MqConstants;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @descript 购物车的mq消息监听
 */
@Component
public class CartMqListener {

    @Resource
    private CartService cartService;

    /**
     * 监听订单服务修改购物车数据的业务
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstants.CART_CLEAR_QUEUE),
            exchange = @Exchange(MqConstants.ORDER_EXCHANGE),
            key = MqConstants.CART_CLEAR_KEY)
    )
    public void clearCart(List<Long> cartIds) {
        cartService.clearIds(cartIds);
    }
}
