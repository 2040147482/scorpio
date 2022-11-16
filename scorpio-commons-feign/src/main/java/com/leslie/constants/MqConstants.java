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
}
