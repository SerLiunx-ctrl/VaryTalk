package com.serliunx.varytalk.system.config;

import com.serliunx.varytalk.framework.core.interceptor.LogInterceptor;
import com.serliunx.varytalk.framework.core.interceptor.RateLimitInterceptor;
import com.serliunx.varytalk.system.interceptor.GlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final GlobalInterceptor globalInterceptor;
    private final LogInterceptor logInterceptor;
    private final RateLimitInterceptor rateLimitInterceptor;

    public WebConfiguration(GlobalInterceptor globalInterceptor,
                            LogInterceptor logInterceptor,
                            RateLimitInterceptor rateLimitInterceptor) {
        this.globalInterceptor = globalInterceptor;
        this.logInterceptor = logInterceptor;
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册请求耗时统计拦截器
        registry.addInterceptor(logInterceptor);
        //注册接口速率限制拦截器
        registry.addInterceptor(rateLimitInterceptor);
        //注册通用拦截器
        registry.addInterceptor(globalInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/login")
                .excludePathPatterns("/auth/register");
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://serliunx.tpddns.cn");
        corsConfiguration.addAllowedOrigin("localhost");
        //2) 是否发送Cookie信息
        corsConfiguration.setAllowCredentials(true);
        //3) 允许的请求方式
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("HEAD");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("PATCH");
        // 4）允许的头信息
        corsConfiguration.addAllowedHeader("*");
        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", corsConfiguration);
        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}
