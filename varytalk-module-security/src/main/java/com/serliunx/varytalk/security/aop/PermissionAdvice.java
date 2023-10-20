package com.serliunx.varytalk.security.aop;

import com.serliunx.varytalk.common.annotation.PermissionRequired;
import com.serliunx.varytalk.common.annotation.PermitAll;
import com.serliunx.varytalk.common.exception.AuthenticationConflictException;
import com.serliunx.varytalk.common.exception.ServiceException;
import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.SystemUserService;
import com.serliunx.varytalk.security.service.PermissionService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PermissionAdvice {

    private final SystemUserService systemUserService;
    private final PermissionService permissionService;

    public PermissionAdvice(SystemUserService systemUserService,
                            PermissionService permissionService) {

        this.systemUserService = systemUserService;
        this.permissionService = permissionService;
    }

    @Before("com.serliunx.varytalk.common.aop.PointCutDefinition.permissionPoint()")
    public void permissionCheck(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if(method.getAnnotation(PermitAll.class) != null){
            throw new AuthenticationConflictException("权限鉴定错误, 通常是服务器权限鉴定逻辑出错!",
                    joinPoint.getTarget().getClass().getName() + "." + method.getName() + "()");
        }
        Long userId = SecurityUtils.getUserId();
        PermissionRequired annotation = method.getAnnotation(PermissionRequired.class);
        if(annotation == null){
            return;
        }
        String permission = annotation.value();
        SystemUser systemUser = systemUserService.selectUserById(userId);
        boolean result = permissionService.hasPermission(systemUser, permission);
        if(!result){
            throw new ServiceException("权限不足, 无法进行此操作!", 400);
        }
    }
}
