package com.leslie.order.listener;

import com.leslie.order.service.OrderService;
import com.leslie.vo.OrderStatusParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 20110
 * @descript 监听Redis的key过期回调
 */
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {


    @Resource
    private OrderService orderService;


    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 使用该方法监听，当我们key过期是自动调用该方法
     *
     * @param message
     * @param pattern
     */
    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] pattern) {
        //过期的key
        String key = message.toString();
        String orderId = key.split(":")[1];

        OrderStatusParam orderStatusParam = new OrderStatusParam();
        orderStatusParam.setOrderId(orderId);
        orderStatusParam.setOrderStatus(4);
        orderService.updateOrderStatus(orderStatusParam);
        log.info("订单编号为: " + orderId + "订单状态为已取消");
    }

}