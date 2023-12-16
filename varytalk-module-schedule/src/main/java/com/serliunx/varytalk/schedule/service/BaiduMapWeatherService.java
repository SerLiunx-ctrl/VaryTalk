package com.serliunx.varytalk.schedule.service;

import com.serliunx.varytalk.schedule.entity.baidu.BaiduMapResponseData;
import com.serliunx.varytalk.schedule.entity.baidu.weather.WeatherDataNow;

/**
 * @author SerLiunx
 * @since 1.0
 */
public interface BaiduMapWeatherService {

    BaiduMapResponseData<WeatherDataNow> getWeatherNow(String districtId);
}
