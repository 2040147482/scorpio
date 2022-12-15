package com.leslie.clients;

import com.leslie.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 20110
 * @descript 后台管理服务调用订单服务client
 */
@FeignClient("order-service")
public interface OrderClient {

    /**
     * 订单分页查询
     *
     * @param curPage 当前页码
     * @param size 页容量
     * @return
     */
    @GetMapping("/order/{curPage}/{size}")
    Result queryPage(@PathVariable("curPage") Integer curPage,
                     @PathVariable("size") Integer size);


    /**
     * 查询单个订单信息
     *
     * @param orderId 订单编号
     */
    @GetMapping("/order/one/{orderId}")
    Result one(@PathVariable("orderId") String orderId);
}