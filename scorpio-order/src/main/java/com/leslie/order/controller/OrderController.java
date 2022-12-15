package com.leslie.order.controller;

import com.leslie.order.service.OrderService;
import com.leslie.utils.Result;
import com.leslie.vo.OrderFromCartParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;;

/**
 * @author 20110
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 查询单个订单信息
     *
     * @param orderId 订单编号
     */
    @GetMapping("/one/{orderId}")
    public Result one(@PathVariable("orderId") String orderId) {
        return orderService.one(orderId);
    }

    /**
     * 查询全部订单接口
     */
    @GetMapping("/list/{userId}")
    public Result list(@PathVariable("userId") Long userId) {
        return orderService.show(userId);
    }

    @PostMapping("/addfromcart")
    public Result addFromCart(@RequestBody OrderFromCartParam orderFromCartParam) {
        return orderService.addFromCart(orderFromCartParam);
    }

    @DeleteMapping("/cancel/{orderId}")
    public Result cancel(@PathVariable("orderId") String orderId) {
        return orderService.cancel(orderId);
    }

    @GetMapping("/{curPage}/{size}")
    public Result queryPage(@PathVariable("curPage") Integer curPage,
                            @PathVariable("size") Integer size) {
        return orderService.queryPage(curPage, size);
    }

}
