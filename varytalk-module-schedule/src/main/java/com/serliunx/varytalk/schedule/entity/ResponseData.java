package com.serliunx.varytalk.schedule.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 响应体封装
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class ResponseData<T> {
    private String message;
    private Integer status;
    private T data;
}
