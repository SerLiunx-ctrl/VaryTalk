package com.serliunx.varytalk.cache.annotation;

import com.serliunx.varytalk.cache.processor.CacheProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 缓存注解, 使用了该注解的方法可以将其返回值按照一定的规则存储到缓存中.
 * <p> 目前仅支持Redis, 缓存不建议使用在以下场景:
 * <li> 分页查询
 * <li> 对数据实时性要求非常高(因为即使使用的缓存自动更新也会存在几毫秒的延迟)
 * 因为延迟的存在可能需要再次请求接口才能获取最新数据.
 * <p> 建议在以下场景使用该注解:
 * <li>调用指定方法时、一次性从数据库拿所有相关数据，如：所有标签、所有板块、标签等。
 * <li>特定数据访问频率极高且数据库中所含字段数量多、对数据库性能要求偏高的数据，如：用户所有信息
 * <p>
 *
 * @author SerLiunx
 * @since 1.0
 * @see CacheProcessor
 * @see CacheRefresh
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

    /**
     * 是否在调用方法时强制刷新缓存
     * <li>主要用于定时任务, 主动调用的方法请保持默认值
     */
    boolean forceRefresh() default false;
}
