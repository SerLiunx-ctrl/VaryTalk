package com.serliunx.varytalk.httpclient.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义HTTP客户端的注解
 * <li> 注解必须使用在接口上, 否则无法生效.
 * @author SerLiunx
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Client {

    /**
     * Bean的名称
     */
    String value() default "";

    /**
     * 该客户端的接口路径
     */
    String url();
}
