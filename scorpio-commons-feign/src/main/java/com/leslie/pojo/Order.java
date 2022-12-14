package com.leslie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 订单表
 * @author 20110
 * @TableName tb_order
 */
@TableName(value ="tb_order")
@Data
public class Order implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 用户地址主键id
     */
    private Integer addressId;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 支付状态，0:待支付，1:已支付
     */
    private Integer payStatus;

    /**
     * 订单状态，0:待付款，1:待发货，2:待收货，3:待评价，4:已取消
     */
    private Integer orderStatus;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}