package com.leslie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 评分表
 * @author 20110
 * @TableName tb_score
 */
@TableName(value ="tb_score")
@Data
public class Score implements Serializable {
    /**
     * 评分主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户主键id
     */
    private Long userId;

    /**
     * 商品主键id
     */
    private Long productId;

    /**
     * 评分，1：非常好:5，很好:4，好:3，差:2，很差:1
     */
    private Integer score;

    /**
     * 评论
     */
    private String comment;

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