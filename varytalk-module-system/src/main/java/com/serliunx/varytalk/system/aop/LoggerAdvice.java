package com.serliunx.varytalk.system.aop;

import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.executor.SyncTaskExecutor;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.common.util.ServletUtils;
import com.serliunx.varytalk.system.entity.SystemLog;
import com.serliunx.varytalk.system.service.SystemLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAdvice {

    private final SystemLogService systemLogService;
    private final SyncTaskExecutor syncTaskExecutor;

    public LoggerAdvice(SystemLogService systemLogService,
                        SyncTaskExecutor syncTaskExecutor) {
        this.systemLogService = systemLogService;
        this.syncTaskExecutor = syncTaskExecutor;
    }

    @Around("com.serliunx.varytalk.common.aop.PointCutDefinition.logPoint()")
    public Object apiLogger(ProceedingJoinPoint joinPoint){
        Object result;
        try {
            result = joinPoint.proceed();
            if(result instanceof Result resp){
                MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                Logger annotation = methodSignature.getMethod().getAnnotation(Logger.class);
                if(annotation == null){
                    return result;
                }
                String ip = ServletUtils.getIp();
                String requestURI = ServletUtils.getRequestURI();
                Long userId = SecurityUtils.getUserId();
                String opName = annotation.opName();
                String opContext = annotation.value() + " 状态信息: " + resp.getMessage();
                final SystemLog systemLog = SystemLog.getBuilder()
                        .setApiPath(requestURI)
                        .setOpContext(opContext)
                        .setOpName(opName)
                        .setUserId(userId)
                        .setIpAddress(ip)
                        .now().build();
                if(annotation.saveToSql()){
                    syncTaskExecutor.submit(() -> systemLogService.insertLog(systemLog));
                }
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
