package com.serliunx.varytalk.security;

import org.springframework.core.Ordered;

/**
 * 安全拦截器、主要用于接口鉴权相关操作
 * @author SerLiunx
 * @since 1.0
 */
public interface SecurityInterceptor extends Ordered{

    /**
     * 指定接口执行前要进行的操作
     * @param context 安全检查上下文(由系统提供)
     * @return 返回真时继续往下执行其它拦截器、为假时抛出异常,消息为message()中所返回的内容.
     */
    default boolean preHandle(SecurityContext context){
        return true;
    }

    /**
     * 指定接口执行后要进行的操作
     * <li> 当前版本暂时没有用处
     */
    default void postHandle(){}

    /**
     * 当某一拦截器的检查不通过时抛出异常所显示的消息内容
     */
    default String message(){
        return "无权访问, 指定接口安全检查未通过!";
    }

    /**
     * 执行当前拦截器的执行顺序、值越小则优先级越高
     */
    @Override
    default int getOrder(){
        return 0;
    }

    Class<?> getGroup();
}
