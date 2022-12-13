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

    @PostMapping("/addfromcart")
    public Result addFromCart(@RequestBody OrderFromCartParam orderFromCartParam) {
        return orderService.addFromCart(orderFromCartParam);
    }

    @DeleteMapping("/cancel/{orderId}")
    public Result cancel(@PathVariable("orderId") String orderId) {
        return orderService.cancel(orderId);
    }

}
