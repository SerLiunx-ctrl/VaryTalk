package com.serliunx.varytalk.framework.logging.processor;

import com.serliunx.varytalk.framework.logging.annotation.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 日志注解处理器
 * @author SerLiunx
 * @since 1.0
 */
@Aspect
@Component
public class LoggerProcessor {

    public Class<? extends Annotation> annotationClass = Logger.class;

    @Before("@annotation(com.serliunx.varytalk.framework.logging.annotation.Logger)")
    public void process(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        if(hasAnnotation(method)){

        }
    }

    private boolean hasAnnotation(Method method){
        return method.getAnnotation(annotationClass) != null;
    }
}
