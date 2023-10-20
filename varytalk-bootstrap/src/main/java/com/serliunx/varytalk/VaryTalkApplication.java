package com.serliunx.varytalk;

import com.alibaba.druid.pool.DruidDataSource;
import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serluinx.varytalk.console.ConsoleMenu;
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
@EnableConfigurationProperties({SystemAutoConfigurer.class})
public class VaryTalkApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(VaryTalkApplication.class, args);
        DruidDataSource dataSource = applicationContext.getBean(DruidDataSource.class);
        //输入指令
        ConsoleMenu menu = ConsoleMenu.build(applicationContext);
        boolean result = menu.input();
        if(!result){
            dataSource.close();
            applicationContext.close();
        }
    }
}
