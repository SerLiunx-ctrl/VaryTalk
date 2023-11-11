package com.serliunx.varytalk.cache.annotation;

import com.serliunx.varytalk.cache.processor.CacheProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 缓存注解, 使用了该注解的方法可以将其返回值按照一定的规则存储到缓存中.
 * <li> 目前仅支持Redis
 * <p>
 * <p> 缓存不建议使用在以下场景:
 * <li> 分页查询
 * <li> 对数据实时性要求非常高(因为即使使用的缓存自动更新也会存在几毫秒的延迟)
 * @author SerLiunx
 * @since 1.0
 * @see CacheProcessor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    /**
     * 缓存的键值, 留空即使用类名+方法名的方式生成
     */
    String value() default "";

    /**
     * 缓存时间中的单元, 与time()搭配使用
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

    /**
     * 缓存时长, 需要与timeUnit()搭配使用
     */
    int time() default 5;
}
