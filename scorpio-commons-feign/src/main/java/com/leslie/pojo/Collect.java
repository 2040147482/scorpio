package com.leslie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏表
 * @author 20110
 * @TableName tb_collect
 */
@TableName(value ="tb_collect")
@Data
public class Collect implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分类名称
     */
    private Long productId;

    /**
     * 收藏时间
     */
    private Date collectTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}