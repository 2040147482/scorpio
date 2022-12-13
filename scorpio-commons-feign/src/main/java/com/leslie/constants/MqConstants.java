package com.leslie.constants;

/**
 * @author 20110
 */
public class MqConstants {

    /**
     * 交换机
     */
    public final static String PRODUCT_EXCHANGE = "product.topic";

    /**
     * 监听新增和修改的队列
     */
    public final static String PRODUCT_INSERT_QUEUE = "product.insert.queue";

    /**
     * 监听新增或修改的队列
     */
    public final static String PRODUCT_DELETE_QUEUE = "product.delete.queue";

    /**
     * 新增或修改的RoutingKey
     */
    public final static String PRODUCT_INSERT_KEY = "product.insert";

    /**
     * 删除的RoutingKey
     */
    public final static String PRODUCT_DELETE_KEY = "product.delete";

    /**
     * 订单服务topic
     */
    public final static String ORDER_EXCHANGE = "order.topic";


    /**
     * 监听订单服务修改商品库存的队列
     */
    public final static String ORDER_UPDATE_QUEUE = "order.update.queue";


    /**
     * 修改商品库存的RoutingKey
     */
    public final static String ORDER_UPDATE_KEY = "order.update";

    /**
     * 监听订单服务修改商品库存的队列
     */
    public final static String PRODUCT_UPDATE_QUEUE = "product.update.queue";

    /**
     * 修改商品库存的RoutingKey
     */
    public final static String PRODUCT_UPDATE_KEY = "product.update";


    /**
     * 监听订单服务修改购物车数据的队列
     */
    public final static String CART_CLEAR_QUEUE = "cart.clear.queue";

    /**
     * 修改购物车的RoutingKey
     */
    public final static String CART_CLEAR_KEY = "cart.clear";
}
