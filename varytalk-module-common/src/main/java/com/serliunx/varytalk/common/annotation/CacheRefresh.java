package com.serliunx.varytalk.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存更新注解, 配合{@link Cached} 使用
 * <li> 该注解可以标记指定键、方法所对应的缓存需要更新
 * <li> 标记后, {@link Cached} 的方法会在下次请求缓存前从其他存储介质获取最新数据返回并缓存
 * <li> 目前仅支持Redis
 * @see Cached
 * @author SerLiunx
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheRefresh {

    /**
     * {@link Cached} 注解所标注的方法名
     */
    String value();

    /**
     * 需要获取tag的参数类型
     */
    Class<?> clazz();

    /**
     * 参数类型下的属性名作为缓存键值的tag
     */
    String propertyName() default "";

    /**
     * 是否需要对key增加tag值, 用于区分同一个方法不同的传参所产生不同的返回值
     */
    boolean needTag() default true;
}
