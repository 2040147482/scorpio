package com.leslie.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 20110
 * description: 返回结果通用对象  Map
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功状态码
     */
    public static final String SUCCESS_CODE = "666";
    /**
     * 失败状态码
     */
    public static final String FAIL_CODE = "886";

    private String code;
    private String msg;
    private Object data;
    private Long total;


    public static Result ok() {
        return new Result(SUCCESS_CODE, "success", null, null);
    }

    public static Result ok(Object data) {
        return new Result(SUCCESS_CODE, "success", data, null);
    }

    public static Result ok(String msg, Object data) {
        return new Result(SUCCESS_CODE, msg, data, null);
    }

    public static Result ok(List<?> data, long total) {
        return new Result(SUCCESS_CODE, "success", data, total);
    }

    public static Result fail(String errorMsg) {
        return new Result(FAIL_CODE, errorMsg, null, null);
    }
}