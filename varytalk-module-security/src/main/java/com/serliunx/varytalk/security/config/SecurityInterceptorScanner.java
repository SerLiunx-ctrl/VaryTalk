package com.serliunx.varytalk.security.config;

import com.serliunx.varytalk.security.SecurityInterceptor;
import com.serliunx.varytalk.security.builder.InterceptorChainBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Slf4j
@Configuration
@SuppressWarnings("all")
public class SecurityInterceptorScanner implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, SecurityInterceptor> byType = applicationContext.getBeansOfType(SecurityInterceptor.class);

        //拦截器排序
        Collection<SecurityInterceptor> beans = byType.values();
        InterceptorChainBuilder.original = beans.stream()
                .sorted((o1, o2) -> {
                    if (o1.getOrder() > o2.getOrder()) {
                        return 1;
                    } else if (o1.getOrder() < o2.getOrder()) {
                        return -1;
                    }
                    return 0;
                })
                .peek(s -> {
                    log.debug("加载安全拦截器: {}({})", s.getClass().getSimpleName(), s.getOrder());
                })
                .collect(Collectors.toCollection(LinkedList::new));
        log.info("共载入 {} 个安全检查拦截器.", beans.size());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
