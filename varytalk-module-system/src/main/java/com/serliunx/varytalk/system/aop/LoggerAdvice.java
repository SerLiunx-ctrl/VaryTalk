package com.serliunx.varytalk.system.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.executor.SyncTaskExecutor;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.common.util.ServletUtils;
import com.serliunx.varytalk.system.aop.entity.ContextBody;
import com.serliunx.varytalk.system.entity.SystemLog;
import com.serliunx.varytalk.system.service.SystemLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Map;

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

    @Around("@annotation(com.serliunx.varytalk.common.annotation.Logger)")
    public Object apiLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if(result instanceof Result resp){
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            Logger annotation = method.getAnnotation(Logger.class);
            if(annotation == null){
                return result;
            }
            String ip = ServletUtils.getIp();
            Map<String, String[]> parameterMap = ServletUtils.getRequest().getParameterMap();
            String requestURI = ServletUtils.getRequestURI();
            Long userId = SecurityUtils.getUserId();
            String opName = annotation.opName();
            String opContext = annotation.value() + " 状态信息: " + resp.getMessage();

            if(annotation.saveToSql()){
                syncTaskExecutor.submit(() -> {
                    Object[] args = joinPoint.getArgs();
                    Object arg = null;
                    if(args.length > 0){
                        arg = args[0];
                    }
                    String body;
                    String params;
                    try {
                        params = new ObjectMapper().writeValueAsString(parameterMap);
                        if(arg instanceof MultipartFile file){
                            body = ContextBody.fromFile(file);
                        }else{
                            body = new ObjectMapper().writeValueAsString(arg);
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    final SystemLog systemLog = SystemLog.getBuilder()
                            .setApiPath(requestURI)
                            .setOpContext(opContext)
                            .setOpName(opName)
                            .setUserId(userId)
                            .setIpAddress(ip)
                            .setParams(params)
                            .setOpBody(body)
                            .now().build();
                    systemLogService.insertLog(systemLog);
                });
            }
        }
        return result;
    }
}
