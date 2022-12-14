package com.leslie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 商品分类表
 * @author 20110
 * @TableName tb_category
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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