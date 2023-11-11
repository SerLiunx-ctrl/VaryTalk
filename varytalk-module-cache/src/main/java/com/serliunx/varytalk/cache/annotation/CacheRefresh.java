package com.serliunx.varytalk.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheRefresh {

    /**
     * 自定义键值
     */
    String value() default "";

    /**
     * 获取缓存的方法名
     */
    String method() default "";

    /**
     * 自定义键值
     */
    String key() default "";
}
