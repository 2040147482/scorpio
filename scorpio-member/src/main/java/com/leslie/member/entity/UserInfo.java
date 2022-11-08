package com.leslie.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信息表
 *
 * @author 20110
 * @TableName tb_user_info
 */
@TableName(value = "tb_user_info")
@Data
public class UserInfo implements Serializable {
    /**
     * 主键自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * member_user_login表的自增ID
     */
    private Long loginId;

    /**
     * 用户的真实姓名
     */
    private String name;

    /**
     * 证件类型 1身份证，2军官证，3护照
     */
    private Integer identityCardType;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 性别，1：男，2：女
     */
    private Integer gender;

    /**
     * 地址
     */
    private String address;

    /**
     * 会员积分
     */
    private Integer userPoint;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 会员级别，0~9级,0代表未开通会员
     */
    private Integer userLevel;

    /**
     * 用户余额
     */
    private BigDecimal userMoney;

    /**
     * 修改时间
     */
    private Date modifiedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}