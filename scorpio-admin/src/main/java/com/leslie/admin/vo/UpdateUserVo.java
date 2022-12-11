package com.leslie.admin.vo;

import lombok.Data;

/**
 * @author 20110
 */
@Data
public class UpdateUserVo {

    private Integer id;
    private String phone;
    private String username;
    private String nickName;
    private Integer status;
}
