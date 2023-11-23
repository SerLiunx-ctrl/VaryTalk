package com.serliunx.varytalk.httpclient.client;

import com.serliunx.varytalk.httpclient.annotation.Client;
import com.serliunx.varytalk.httpclient.entity.baidu.BaiduMapResponseData;
import com.serliunx.varytalk.httpclient.entity.baidu.weather.WeatherDataNow;
import feign.Param;
import feign.RequestLine;

/**
 * 百度地图-天气接口
 * @author SerLiunx
 * @since 1.0
 */
@Client(url = "https://api.map.baidu.com/weather")
public interface BaiduMapWeatherClient {

    /**
     * 根据地区id获取当前的天气情况
     * @param districtId 地区id
     * @param ak 百度地图开放平台ak
     */
    @RequestLine("GET /v1/?district_id={districtId}&data_type=now&ak={ak}")
    BaiduMapResponseData<WeatherDataNow> getWeatherNow(@Param("districtId") String districtId, @Param("ak") String ak);
}
