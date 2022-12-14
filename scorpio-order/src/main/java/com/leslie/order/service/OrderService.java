package com.leslie.order.service;

import com.leslie.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.utils.Result;
import com.leslie.vo.OrderFromCartParam;
import com.leslie.vo.OrderStatusParam;

/**
 * @author 20110
 * @description 针对表【tb_order(订单表)】的数据库操作Service
 * @createDate 2022-12-12 22:52:06
 */
public interface OrderService extends IService<Order> {

    /**
     * 从购物车生成订单
     *
     * @param orderFromCartParam
     * @return
     */
    Result addFromCart(OrderFromCartParam orderFromCartParam);

    /**
     * 取消订单业务
     *
     * @param orderId 订单编号
     * @return
     */
    Result cancel(String orderId);

    /**
     * 修改订单状态
     *
     * @param orderStatusParam 订单编号、订单状态
     */
    int updateOrderStatus(OrderStatusParam orderStatusParam);

    /**
     * 查询单个订单信息
     *
     * @param orderId 订单编号
     * @return
     */
    Result one(String orderId);

    /**
     * 查询全部订单信息
     *
     * @param userId 用户id
     * @return
     */
    Result show(Long userId);
}
