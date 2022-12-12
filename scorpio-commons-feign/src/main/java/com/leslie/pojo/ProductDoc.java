package com.leslie.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 20110
 * description: 用于保存es文档数据
 */
@Data
@NoArgsConstructor
public class ProductDoc {

    private Long productId;
    private String productName;
    private Long categoryId;
    private Double price;
    private Integer productStatus;
    private Integer productStock;
    private Integer productSales;
    private String imageUrl;
    private String all;

    public ProductDoc(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.categoryId = product.getCategoryId();
        this.price = product.getPrice();
        this.productStatus = product.getProductStatus();
        this.productStock = product.getProductStock();
        this.productSales = product.getProductSales();
        this.imageUrl = product.getImageUrl();
        this.all = product.getProductName();
    }

}
