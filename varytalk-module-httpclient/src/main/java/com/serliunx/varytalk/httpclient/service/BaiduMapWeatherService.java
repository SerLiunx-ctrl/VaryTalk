package com.serliunx.varytalk.httpclient.service;

import com.serliunx.varytalk.cache.annotation.Cache;
import com.serliunx.varytalk.cache.annotation.TagValue;
import com.serliunx.varytalk.httpclient.client.BaiduMapWeatherClient;
import com.serliunx.varytalk.httpclient.entity.baidu.BaiduMapResponseData;
import com.serliunx.varytalk.httpclient.entity.baidu.weather.WeatherDataNow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class BaiduMapWeatherService {

    private final BaiduMapWeatherClient baiduMapWeatherClient;

    @Value("${talk-system.baidu-ak}")
    private String baiduAk;

    public BaiduMapWeatherService(BaiduMapWeatherClient baiduMapWeatherClient) {
        this.baiduMapWeatherClient = baiduMapWeatherClient;
    }

    /**
     * 根据地域id获取目前天气信息
     * <li> 默认在缓存中存储30分钟
     * @param districtId 地域id
     */
    @Cache(time = 30)
    public BaiduMapResponseData<WeatherDataNow> getWeatherNow(@TagValue("districtId") String districtId){
        return baiduMapWeatherClient.getWeatherNow(districtId, baiduAk);
    }
}
