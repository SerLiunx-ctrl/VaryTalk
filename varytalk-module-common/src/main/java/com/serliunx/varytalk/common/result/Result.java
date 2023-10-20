package com.serliunx.varytalk.common.result;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class Result {
    private String message;
    private int status;
    private Object data;

    public Result() {
    }

    public Result(Object data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static Result success(Object data, String message, int status) {
        return new Result(data, message, status);
    }

    public static Result success(Object data, String message) {
        return success(data, message, 200);
    }

    public static Result success(Object data) {
        return success(data, "操作成功!");
    }

    public static Result success() {
        return success(null, "操作成功!");
    }

    public static Result fail(Object data, String message, int status) {
        return new Result(data, message, status);
    }

    public static Result fail(Object data, String message) {
        return fail(data, message, 400);
    }

    public static Result fail(String message) {
        return fail(null, message, 400);
    }

    public static Result fail() {
        return fail("操作失败!");
    }

    public static <T> Result pageResult(List<? extends T> list){
        return success(PageResult.page(new PageInfo<T>(list)));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
