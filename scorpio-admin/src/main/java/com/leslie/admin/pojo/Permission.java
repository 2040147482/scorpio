package com.leslie.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 权限表
 * @TableName tb_permission
 */
@TableName(value ="tb_permission")
@Data
public class Permission implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Integer id;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 状态，1：启用，0：未启用
     */
    private Integer status;

    /**
     * 图标
     */
    private String icon;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private List<Permission> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}