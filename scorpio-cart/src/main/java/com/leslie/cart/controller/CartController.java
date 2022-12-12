package com.leslie.cart.controller;

import com.leslie.cart.service.CartService;
import com.leslie.pojo.Cart;
import com.leslie.utils.Result;
import com.leslie.vo.CartSaveParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20110
 * @descript 购物车接口
 */
@RestController
@RequestMapping("cart")
public class CartController {

    @Resource
    private CartService cartService;

    @GetMapping("/show/{userId}")
    public Result show(@PathVariable("userId") Long userId) {
        return cartService.show(userId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CartSaveParam cartSaveParam) {
        if (cartSaveParam == null) {
            return Result.fail("参数为null，添加失败！");
        }
        return cartService.add(cartSaveParam);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Cart cart) {
        if (cart == null) {
            return Result.fail("参数为null，添加失败！");
        }
        return cartService.updateStock(cart);
    }

    @DeleteMapping("/delete/{userId}/{productId}")
    public Result delete(@PathVariable("userId") Long userId,
                         @PathVariable("productId") Long productId) {
        return cartService.delete(userId, productId);
    }


}
