package com.leslie.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leslie.pojo.Cart;
import com.leslie.pojo.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 20110
 * @descript 购物车返回视图对象（订单服务复用）
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class CartVo implements Serializable {

    /**
     * 购物车id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品显示图片
     */
    private String productImg;

    /**
     * 商城价格
     */
    private Double price;

    /**
     * 商品购买数量
     */
    private Integer num;

    /**
     * 商品限购数量
     */
    private Integer maxNum;

    /**
     * 是否勾选
     */
    private Boolean check = false;

    public CartVo(Product product, Cart cart) {
        this.id = cart.getId();
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productImg = product.getImageUrl();
        this.price = product.getPrice();
        this.num = cart.getNum();
        this.maxNum = product.getProductStock();
        this.check = false;
    }
}