package com.leslie.cart.service;

import com.leslie.pojo.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.utils.Result;
import com.leslie.vo.CartSaveParam;

import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_cart(购物车表)】的数据库操作Service
 * @createDate 2022-12-12 14:20:34
 */
public interface CartService extends IService<Cart> {

    /**
     * 购物车商品展示
     *
     * @param userId 用户id
     * @return
     */
    Result show(Long userId);

    /**
     * 添加商品到购物车
     *
     * @param cartSaveParam 用户id、商品id
     * @return 666 或 886
     */
    Result add(CartSaveParam cartSaveParam);

    /**
     * 修改购物车信息
     *
     * @param cart
     * @return
     */
    Result updateStock(Cart cart);

    /**
     * 根据用户id和商品id删除购物车中商品
     *
     * @param userId 用户id
     * @param productId 商品id
     * @return
     */
    Result delete(Long userId, Long productId);

    /**
     * 清空对应id购物车项
     * @param cartIds 购物车id集合
     */
    void clearIds(List<Long> cartIds);
}
