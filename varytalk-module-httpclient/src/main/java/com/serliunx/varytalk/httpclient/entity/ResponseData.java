package com.serliunx.varytalk.httpclient.entity;

/**
 * 响应体封装
 * @author SerLiunx
 * @since 1.0
 */
public class ResponseData<T> {
    private String message;
    private Integer status;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
