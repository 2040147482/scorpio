package com.leslie.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 地址表
 * @author 20110
 * @TableName tb_address
 */
@TableName(value ="tb_address")
@Data
public class Address implements Serializable {
    /**
     * 地址主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户主键user_id
     */
    private Long userId;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 详细地址
     */
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}