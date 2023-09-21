package github.serliunx.varytalk.common.aop;

import github.serliunx.varytalk.common.annotation.Logger;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.common.util.AopUtils;
import github.serliunx.varytalk.common.util.SecurityUtils;
import github.serliunx.varytalk.common.util.ServletUtils;
import github.serliunx.varytalk.project.system.entity.SystemLog;
import github.serliunx.varytalk.project.system.service.SystemLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggerAdvice {

    private final SystemLogService systemLogService;

    public LoggerAdvice(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    @Around("github.serliunx.varytalk.common.aop.PointCutDefinition.logPoint()")
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
                String requestURI = ServletUtils.getRequest().getRequestURI();
                Long userId = SecurityUtils.getUserId();
                String opName = annotation.opName();
                String opContext = annotation.value() + " 状态信息: " + resp.getMessage();
                SystemLog systemLog = SystemLog.getBuilder()
                        .setApiPath(requestURI)
                        .setOpContext(opContext)
                        .setOpName(opName)
                        .setUserId(userId)
                        .setIpAddress(ip)
                        .now().build();
                if(annotation.saveToSql()){
                    systemLogService.insertLog(systemLog);
                }
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
