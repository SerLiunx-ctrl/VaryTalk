package com.serliunx.varytalk;

import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 论坛后台服务启动类
 *
 * @author SerLiunx
 * @since 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.serliunx.varytalk"})
@EnableConfigurationProperties({SystemAutoConfigurer.class})
public class VaryTalkApplication {
    public static void main(String[] args) {
        SpringApplication.run(VaryTalkApplication.class, args);
    }
}
