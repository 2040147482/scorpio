package com.leslie.recommender.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName(value ="tb_product")
@Data
public class Products {
    /**
     * 商品id
     */
    @TableId(type = IdType.AUTO)
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 商品评价评分
     */
    private Double avgScore;

    /**
     * 上架状态，1：上架，0：未上架
     */
    private Integer productStatus;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 已卖数量
     */
    private Integer productSales;

    /**
     * 图片路径
     */
    private String imageUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
