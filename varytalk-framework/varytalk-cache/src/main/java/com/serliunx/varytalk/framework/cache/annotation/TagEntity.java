package com.serliunx.varytalk.framework.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存键标签实体, 与{@link TagValue} 配合
 * @author SerLiunx
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface TagEntity {

    String[] ignore() default {};
}
