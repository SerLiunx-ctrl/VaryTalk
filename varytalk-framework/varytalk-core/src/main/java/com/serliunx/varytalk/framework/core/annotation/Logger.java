package com.serliunx.varytalk.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统内置Logger注解
 * <p>
 * 该注解可以自动记录操作日志, 并控制记录方式: 直接打印、数据库
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logger {

    /**
     * 是否打印日志
     */
    boolean print() default true;

    /**
     * 是否保存日志到数据库中
     */
    boolean saveToSql() default true;

    /**
     * 操作名称&类型
     */
    String opName() default "";

    /**
     * 操作具体信息
     */
    String value();
}
