package com.serliunx.varytalk.framework.logging.processor;

import com.serliunx.varytalk.framework.core.executor.AsyncTaskExecutor;
import com.serliunx.varytalk.framework.logging.annotation.Logger;
import com.serliunx.varytalk.framework.logging.constant.LogLevel;
import com.serliunx.varytalk.framework.logging.constant.LogStorageType;
import com.serliunx.varytalk.framework.logging.entity.CoreLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 日志注解处理器
 * @author SerLiunx
 * @since 1.0
 */
@Aspect
@Component
public class LoggerProcessor {

    public Class<Logger> annotationClass = Logger.class;

    private final AsyncTaskExecutor asyncTaskExecutor;

    public LoggerProcessor(AsyncTaskExecutor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Before("@annotation(com.serliunx.varytalk.framework.logging.annotation.Logger)")
    public void process(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Logger annotation = method.getAnnotation(annotationClass);
        if(annotation != null){
            String requestBody = getRequestBody(joinPoint);
            LogLevel logLevel = annotation.level();
            LogStorageType[] logStorageTypes = annotation.storageType();
            String description = annotation.value();

            boolean async = annotation.isAsync();
            if(async){
                asyncTaskExecutor.submit(() -> {
                    CoreLog log = new CoreLog();
                    log.setDescription(description);
                    log.setLevel(logLevel);
                });
            }else{

            }
        }
    }

    private CoreLog generateLog(){
        return null;
    }

    private String getRequestBody(JoinPoint joinPoint){

        return null;
    }

    private String getRequestHeader(JoinPoint joinPoint){
        return null;
    }
}
