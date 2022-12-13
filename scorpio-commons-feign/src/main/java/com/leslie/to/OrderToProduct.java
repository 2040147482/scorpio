package com.leslie.to;


import lombok.Data;

/**
 * @author 20110
 * @descript 订单服务发送商品服务的传输对象
 */
@Data
public class OrderToProduct {

    private Long productId;
    private Integer num;
}
