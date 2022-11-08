package com.leslie.member.vo;


import com.leslie.member.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 20110
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String errorMsg;
    private Object data;


    public static Result ok() {
        return new Result(Status.SUCCESS.getCode(), null, null);
    }

    public static Result ok(Object data) {
        return new Result(Status.SUCCESS.getCode(), null, data);
    }

    public static Result ok(List<Object> data) {
        return new Result(Status.SUCCESS.getCode(), null, data);
    }

    public static Result fail(String errorMsg) {
        return new Result(Status.ERROR.getCode(), errorMsg, null);
    }
}