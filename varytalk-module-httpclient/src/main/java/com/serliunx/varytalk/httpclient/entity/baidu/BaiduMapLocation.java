package com.serliunx.varytalk.httpclient.entity.baidu;

import lombok.Getter;
import lombok.Setter;

/**
 * 百度地图 - 位置实体
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class BaiduMapLocation {

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县名称
     */
    private String name;

    /**
     * 区县id
     */
    private String id;
}
