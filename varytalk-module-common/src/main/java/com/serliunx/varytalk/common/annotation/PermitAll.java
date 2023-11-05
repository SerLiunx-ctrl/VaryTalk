package com.serliunx.varytalk.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略token鉴权.
 * <p>
 * 禁止和 {@link RequiredPermission} 及{@link RequiredRole} 同时使用!
 * @author SerLiunx
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface PermitAll {

    /**
     * 是否忽略
     */
    boolean value() default true;
}
