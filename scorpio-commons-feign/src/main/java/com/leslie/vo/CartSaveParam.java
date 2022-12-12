package com.leslie.vo;

import lombok.Data;

/**
 * @author 20110
 * @descript 购车车添加参数接收
 */
@Data
public class CartSaveParam {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long productId;
}
