package com.leslie.member.vo;

import lombok.Data;

/**
 * @author 20110
 * @descript 添加地址传递参数对象
 */
@Data
public class AddAddressVo {

    private Long userId;
    private String linkman;
    private String phone;
    private String address;
}
