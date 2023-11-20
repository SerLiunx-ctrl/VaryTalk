package com.serliunx.varytalk.httpclient.support;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * HTTP客户端扫描器
 * @author SerLiunx
 * @since 1.0
 */
@SuppressWarnings("all")
public class ClassPathClientScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathClientScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    /**
     * 重写Bean定义的条件、让其可以扫描到接口
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
}
