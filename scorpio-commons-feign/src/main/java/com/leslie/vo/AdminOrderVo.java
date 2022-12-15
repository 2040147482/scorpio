package com.leslie.vo;

import com.leslie.pojo.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 20110
 */
@Data
@NoArgsConstructor
public class AdminOrderVo {

    private String orderId;
    private String buyerName;
    private String productName;
    private Integer productNum;
    private BigDecimal orderPrice;
    private Integer payStatus;
    private Integer orderStatus;
    private Date orderTime;

    public AdminOrderVo(Order order) {
        this.orderId = order.getOrderId();
        this.productNum = order.getProductNum();
        this.orderPrice = order.getProductPrice();
        this.payStatus = order.getPayStatus();
        this.orderStatus = order.getOrderStatus();
        this.orderTime = order.getOrderTime();
    }
}
