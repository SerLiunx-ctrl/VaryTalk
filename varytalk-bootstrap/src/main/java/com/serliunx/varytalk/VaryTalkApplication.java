package com.serliunx.varytalk;

import com.serliunx.vartalk.plugin.PluginLoader;
import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.configuration.SystemInitializer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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
        PluginLoader pluginLoader = PluginLoader.getInstance();
        pluginLoader.load();
        ConfigurableApplicationContext applicationContext = SpringApplication.run(VaryTalkApplication.class, args);
        //加载插件, 将插件加载器注册到Spring Bean容器中
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.registerSingleton("varyTalkPluginLoader", pluginLoader);
        pluginLoader.enable();
        //初始化
        new SystemInitializer(applicationContext).init();
    }
}
