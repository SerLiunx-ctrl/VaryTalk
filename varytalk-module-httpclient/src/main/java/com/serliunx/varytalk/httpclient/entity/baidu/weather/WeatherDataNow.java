package com.serliunx.varytalk.httpclient.entity.baidu.weather;

import com.serliunx.varytalk.httpclient.entity.baidu.BaiduMapLocation;
import lombok.Getter;
import lombok.Setter;

/**
 * 百度天气响应封装 - 现在
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class WeatherDataNow {
    private BaiduMapLocation location;
    private BaiduWeatherData now;
}
