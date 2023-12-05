package com.serliunx.varytalk.security.support;

import com.serliunx.varytalk.security.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import static com.serliunx.varytalk.security.support.ValidationChainBuilder.VALIDATORS;

/**
 * 校验器扫描
 * @author SerLiunx
 * @since 1.0
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class ValidatorScanner implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //在web容器创建之前获取所有已被Spring容器扫描到的并实现了验证器接口的bean.
        Map<String, Validator> matchedBeans = applicationContext.getBeansOfType(Validator.class);

        //对验证器bean进行排序并初始化
        //为链化的校验器需要独立处理、不在此处加载
        VALIDATORS = matchedBeans.values()
                .stream()
                .sorted()
                .filter(Validator::toChain)
                .peek(s ->
                        log.debug("成功载入校验器 -> {}", s.getClass())
                )
                .collect(Collectors.toCollection(LinkedList::new));
        log.info("成功载入 {} 个校验器!", VALIDATORS.size());
    }
}
