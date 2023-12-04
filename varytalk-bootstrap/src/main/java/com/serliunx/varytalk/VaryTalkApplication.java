package com.serliunx.varytalk;

import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.configuration.SystemInitializer;
import com.serliunx.varytalk.httpclient.config.HttpProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 论坛后台服务启动类
 *
 * @author SerLiunx
 * @since 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.serliunx.varytalk"})
@EnableConfigurationProperties({SystemAutoConfigurer.class, HttpProperties.class})
public class VaryTalkApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(VaryTalkApplication.class, args);
        //初始化
        new SystemInitializer(applicationContext).init();
    }
}
