package com.serliunx.varytalk.framework.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统内用户刷新缓存的注解，通常配合{@link Cache}使用.
 * <p> 使用注意事项
 * <li> 配合{@link Cache}使用时, 请务必指定方法名.
 * <li> 不配合{@link Cache}使用, 或{@link Cache}也试用了自定义键值.
 * 只需要指定键值即可. 即自定义键值和方法名只会有一个影响Redis缓存中所对应的键值.
 * 且自定义的键值的优先级高于指定方法名所生成缓存键值.
 * @author SerLiunx
 * @since 1.0
 * @see Cache
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheRefresh {

    /**
     * 自定义键值
     */
    String value() default "";

    /**
     * 获取缓存的方法名
     */
    String[] method() default "";

}
