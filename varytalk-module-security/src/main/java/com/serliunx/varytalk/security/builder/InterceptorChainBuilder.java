package com.serliunx.varytalk.security.builder;

import com.serliunx.varytalk.security.SecurityInterceptor;

import java.util.LinkedList;

/**
 * @author SerLiunx
 * @since 1.0
 */
public final class InterceptorChainBuilder {

    public static LinkedList<SecurityInterceptor> original = null;

    private InterceptorChainBuilder(){}

    public static LinkedList<SecurityInterceptor> build(){
        if(original == null){
            throw new RuntimeException("安全拦截器链还未初始化!");
        }
        return new LinkedList<>(original);
    }
}
