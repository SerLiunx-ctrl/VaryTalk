package com.serliunx.varytalk.httpclient.entity.baidu.weather;

import lombok.Getter;
import lombok.Setter;

/**
 * 百度天气封装
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class BaiduWeatherData {

    /**
     * 天气现象
     */
    private String text;

    /**
     * 温度（℃）
     */
    private Integer temp;

    /**
     * 体感温度(℃)
     */
    private Integer feelsLike;

    /**
     * 相对湿度(%)
     */
    private Integer th;

    /**
     * 风力等级
     */
    private String windClass;

    /**
     * 风向描述
     */
    private String windDir;

    /**
     * 数据更新时间，北京时间
     */
    private String uptime;
}
