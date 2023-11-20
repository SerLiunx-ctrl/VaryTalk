package com.serliunx.varytalk.httpclient.support;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * 自定义一个可以扫描接口注解的的过滤器
 * @author SerLiunx
 * @since 1.0
 */
public final class InterfaceWithAnnotationTypeFilter implements TypeFilter {

    private final Class<? extends Annotation> annotationType;

    public InterfaceWithAnnotationTypeFilter(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        Class<?> targetClass;
        try {
            targetClass = Class.forName(metadataReader.getClassMetadata().getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return targetClass.isInterface() && targetClass.isAnnotationPresent(annotationType);
    }
}
