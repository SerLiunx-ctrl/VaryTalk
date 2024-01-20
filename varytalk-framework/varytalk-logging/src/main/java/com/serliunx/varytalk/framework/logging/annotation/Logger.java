package com.serliunx.varytalk.framework.logging.annotation;

import com.serliunx.varytalk.framework.logging.constant.LogLevel;
import com.serliunx.varytalk.framework.logging.constant.LogStorageType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录注解
 * @author SerLiunx
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logger {

    /**
     * 日志描述 (可以理解为接口名、服务名)
     */
    String value();

    /**
     * 日志记录级别, 默认{@link LogLevel#DEBUG}
     * @see LogLevel
     */
    LogLevel level() default LogLevel.DEBUG;

    /**
     * 日志存储类型: 多选, 默认只存入数据库.
     */
    LogStorageType[] storageType() default {LogStorageType.MYSQL};

    /**
     * 是否需要异步记录日志(默认为异步)
     */
    boolean isAsync() default true;

    /**
     * 是否将日志信息输出到控制台中
     * <li> 本日志功能旨在使用多种持久化方式存储日志、并不改变原日志框架的功能
     * 故建议保持关闭、除非有需要.
     */
    boolean print() default false;
}
