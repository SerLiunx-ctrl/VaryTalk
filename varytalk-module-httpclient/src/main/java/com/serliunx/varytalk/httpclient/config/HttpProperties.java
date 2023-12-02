package com.serliunx.varytalk.httpclient.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@ConfigurationProperties("talk-system.http-client")
public class HttpProperties {

    /**
     * Http客户端注解包扫描范围
     */
    private String basePackage = "com.serliunx.varytalk.httpclient";
}
