package com.serliunx.varytalk.cache.spring;

import com.serliunx.varytalk.cache.annotation.CacheRefresh;
import com.serliunx.varytalk.cache.api.Refreshable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Configuration
public class CacheConfiguration {
    private final ApplicationContext applicationContext;

    public CacheConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        init();
    }

    private void init(){
        Map<String, Refreshable> matchBeans = applicationContext.getBeansOfType(Refreshable.class);
        for (String key : matchBeans.keySet()) {
            Object bean = applicationContext.getBean(key);
            Class<?> clazz = bean.getClass();
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                if(declaredMethod.getAnnotation(CacheRefresh.class) != null){

                }
            }
        }
    }
}
