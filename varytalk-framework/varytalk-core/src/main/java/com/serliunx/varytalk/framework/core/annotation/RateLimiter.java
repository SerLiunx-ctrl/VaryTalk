package com.serliunx.varytalk.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求速率限制注解
 * <p>
 * <li> 使用该注解可以限制同一ip下指定接口的请求速率
 * @author SerLiunx
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RateLimiter {

    /**
     * 单位时间(秒)
     */
    int time() default 5;

    /**
     * 单位时间内允许请求的次数
     */
    int count() default 2;

    /**
     * 速率限制类型
     *
     * <li> SEPARATELY - 只限制每个ip的请求速率, 即每个ip在某一时间段只能请求指定次数.
     * <li> ALL - 所有ip都限制, 即所有ip在某一时间段只能请求指定次数.(所有人共用一个限制)
     * <p>
     * <li> 如非必要, 不建议使用ALL类型的限制.
     */
    LimitType type() default LimitType.SEPARATELY;

    /**
     * 速率限制类型
     */
    enum LimitType{

        /**
         * 只限制每个ip的请求速率, 即每个ip在某一时间段只能请求指定次数.
         */
        SEPARATELY,

        /**
         * 所有ip都限制, 即所有ip在某一时间段只能请求指定次数.(所有人共用一个限制)
         */
        ALL;
    }
}
