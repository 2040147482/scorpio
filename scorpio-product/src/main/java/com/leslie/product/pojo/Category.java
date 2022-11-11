package com.leslie.product.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 商品分类表
 * @TableName tb_category
 */
@TableName(value ="tb_category")
@Data
public class Category implements Serializable {
    /**
     * 分类id
     */
    @TableId(type = IdType.AUTO)
    private Long cid;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 图标路径
     */
    private String icon;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}