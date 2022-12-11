package com.leslie.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 20110
 */
@Data
public class UserInfoParam {

    private Long userId;
    private String name;
    private String phoneNumber;
    private String userEmail;
    private Integer gender;
    private Date registerTime;

}
