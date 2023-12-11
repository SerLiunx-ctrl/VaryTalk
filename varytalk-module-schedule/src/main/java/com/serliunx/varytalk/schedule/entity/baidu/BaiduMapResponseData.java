package com.serliunx.varytalk.schedule.entity.baidu;

import lombok.Getter;
import lombok.Setter;

/**
 * 百度地图相关接口的返回结果统一封装
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class BaiduMapResponseData<T> {
    private Integer status;
    private T result;
    private String message;
}
