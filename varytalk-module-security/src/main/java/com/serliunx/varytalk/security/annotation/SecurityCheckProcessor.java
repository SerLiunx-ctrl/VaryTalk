package com.serliunx.varytalk.security.annotation;

import com.serliunx.varytalk.common.exception.ServiceException;
import com.serliunx.varytalk.common.util.ServletUtils;
import com.serliunx.varytalk.security.SecurityContext;
import com.serliunx.varytalk.security.SecurityInterceptor;
import com.serliunx.varytalk.security.builder.InterceptorChainBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * 安全检查的主要执行逻辑
 * <p>
 * 目前主要逻辑使用AOP实现、方式并不优雅。每次增强时会重复获取某些固定值，
 * 而这些固定值理应在容器初始化时就已固定。
 * TODO: 后续需要更改实现方式
 */
@Slf4j
@Aspect
@Component
public class SecurityCheckProcessor {

    @Before("@annotation(com.serliunx.varytalk.security.annotation.SecurityCheck)")
    public void process(JoinPoint joinPoint){
        //生成安全检查上下文
        SecurityContext context = getSecurityContext(joinPoint);

        LinkedList<SecurityInterceptor> interceptors = InterceptorChainBuilder.build();
        for (SecurityInterceptor interceptor : interceptors) {

            //当注解所指定组与拦截器所感兴趣的组不一致时、跳过该拦截器的执行
            if(!interceptor.getGroup().equals(context.getGroup())){
                continue;
            }
            //执行拦截器的逻辑, 这个方法才会引起context中的属性的改变.
            //所以请不要和下方的跳过逻辑交换执行顺序.
            boolean result = interceptor.preHandle(context);
            //当某个拦截器设置了跳过剩下的拦截器时、执行跳过逻辑.
            if(context.isSkip()){
                break;
            }
            if(!result){
                throw new ServiceException(interceptor.message(), 400);
            }
        }
    }

    private static SecurityContext getSecurityContext(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object target = joinPoint.getTarget();

        //获得当前注解的信息
        SecurityCheck securityCheck = signature.getMethod().getAnnotation(SecurityCheck.class);
        CheckType checkType = securityCheck.checkType();
        String value = securityCheck.value();
        Class<?> group = securityCheck.group();
        return new SecurityContext(
                target,
                signature,
                ServletUtils.getRequest(),
                ServletUtils.getRequestAttributes().getResponse(),
                checkType,
                value,
                group);
    }
}
