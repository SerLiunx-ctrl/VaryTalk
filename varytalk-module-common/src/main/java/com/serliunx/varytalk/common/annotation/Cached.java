package com.serliunx.varytalk.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 缓存注解, 用在方法上. 将方法的返回值存储到缓存中!
 * <li> 目前仅支持 Redis
 * @author SerLiunx
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Cached {

    /**
     * 缓存中的key, 默认为 "主键:类名:方法名"
     *
     * <li> 主键: 即配置文件中的main-prefix
     * <li> 类名: 类的名称, 但由驼峰命名转换为下划线.
     * <p> 例如: SystemUser -> system_user
     */
    String value() default "";

    /**
     * key中的tag所对应方法中的参数顺序(从0开始), 用于进一步区分缓存值,
     * <li> 填写的为方法参数中的顺序, 目前支持String、Long、Integer简单类型的及其包装类
     * <li> 复杂对象暂时未支持(计划中)
     * <li> 如 {main_key}:{class_name}:{method_name}:{tag}
     * <li> 实例: vary_talk:system_user:select_by_name:root
     */
    int index() default -1;

    /**
     * 缓存时间的时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

    /**
     * 缓存时长, 与<h>timeUnit()</h>共同生效.
     * <li> 为0时, 则永久存在缓存中.
     */
    long time() default 5;
}
