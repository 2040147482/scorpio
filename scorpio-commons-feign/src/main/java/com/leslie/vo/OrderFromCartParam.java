package com.leslie.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 20110
 * @descript 从购物车生成订单接收的参数param
 */
@Data
public class OrderFromCartParam {

    private Long userId;
    private Integer addressId;
    private List<CartVo> products;
}
