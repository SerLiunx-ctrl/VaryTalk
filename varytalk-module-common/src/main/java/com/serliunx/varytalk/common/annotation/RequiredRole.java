package com.serliunx.varytalk.common.annotation;

import com.serliunx.varytalk.common.constants.ValidationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注的接口可以判断当前用户是否属于某个角色
 * <p>
 * 类似{@link RequiredPermission}, 可以同时使用、满足其中一项即可
 *
 * @author SerLiunx
 * @since 1.0
 * @see RequiredPermission
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequiredRole {

    /**
     * 角色id
     */
    String[] value();

    /**
     * 判断类型(方式)
     */
    ValidationType validationType() default ValidationType.INCLUDE;
}
