package com.example.project.common;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一API响应结果封装
 * @param <T> 数据泛型
 */
@Data
public class Result<T> implements Serializable {
    /**
     * 状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功状态码
     */
    private static final int SUCCESS_CODE = 200;

    /**
     * 成功提示信息
     */
    private static final String SUCCESS_MSG = "success";

    /**
     * 成功结果（无数据）
     */
    public static <T> Result<T> ok() {
        return ok(null);
    }

    /**
     * 成功结果（有数据）
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS_CODE);
        result.setMsg(SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    /**
     * 失败结果
     */
    public static <T> Result<T> fail(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 失败结果（使用默认错误码）
     */
    public static <T> Result<T> fail(String msg) {
        return fail(500, msg);
    }
}