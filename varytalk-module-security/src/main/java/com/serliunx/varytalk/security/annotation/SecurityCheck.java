package com.serliunx.varytalk.security.annotation;

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
public @interface SecurityCheck {

    String value() default "";

    /**
     * 对检查器分组, 可以指定起作用的拦截器
     * <li> 用法类似于Validated注解
     */
    Class<?> group();

    /**
     * 检查类型, 默认为值类型。一般用于权限、角色鉴定
     */
    CheckType checkType() default CheckType.VALUE;
}
