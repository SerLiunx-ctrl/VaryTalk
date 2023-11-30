package com.serliunx.varytalk.security;

import com.serliunx.varytalk.security.annotation.CheckType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 安全拦截上下文
 * @author SerLiunx
 * @since 1.0
 */
@Getter
public class SecurityContext {

    /**
     * 安全检查注解所代理的对象
     */
    private final Object target;

    /**
     * MethodSignature
     */
    private final MethodSignature signature;

    /**
     * 当前请求
     */
    private final HttpServletRequest request;

    /**
     * 当前响应
     */
    private final HttpServletResponse response;

    /**
     * 是否跳过余下的安全拦截器
     */
    private boolean skip = false;

    /**
     * 检查类型
     */
    private final CheckType type;

    /**
     * 值检查(当type为VALUE或SPEL时)
     */
    private final String value;

    /**
     * 组检查(当type为CHECK_GROUP时)
     */
    private final Class<?> group;

    public SecurityContext(Object target, MethodSignature signature, HttpServletRequest request,
                           HttpServletResponse response, CheckType type, String value, Class<?> group) {
        this.target = target;
        this.signature = signature;
        this.request = request;
        this.response = response;
        this.type = type;
        this.value = value;
        this.group = group;
    }

    /**
     * 跳过剩下的安全拦截器
     */
    public void skip(){
        skip = true;
    }
}
