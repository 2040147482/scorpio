package com.leslie.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 20110
 */

@Getter
@AllArgsConstructor
public enum Status {
    /**
     * 请求成功：200
     */
    SUCCESS(200, "请求成功"),

    /**
     * 请求失败：404
     */
    ERROR(404, "请求失败");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 描述
     */
    private String message;


}
