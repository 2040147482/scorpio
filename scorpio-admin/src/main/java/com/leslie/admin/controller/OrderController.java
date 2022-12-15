package com.leslie.admin.controller;

import com.leslie.clients.OrderClient;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20110
 * @descript 订单管理API接口
 */
@RestController
@RequestMapping("admin/order")
public class OrderController {

    @Resource
    private OrderClient orderClient;

    /**
     * 分页查询全部订单
     *
     * @param curPage 当前页码
     * @param size 页容量
     * @return
     */
    @GetMapping("/query/{curPage}/{size}")
    public Result queryPage(@PathVariable("curPage") Integer curPage,
                            @PathVariable("size") Integer size) {
        if (curPage == null || size == null) {
            return Result.fail("参数为null，无法查询!");
        }
        return orderClient.queryPage(curPage, size);
    }

    /**
     * 查询单个订单信息
     *
     * @param orderId 订单编号
     */
    @GetMapping("/one/{orderId}")
    public Result one(@PathVariable("orderId") String orderId) {
        return orderClient.one(orderId);
    }

}
