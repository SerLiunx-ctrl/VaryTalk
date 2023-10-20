package com.serliunx.varytalk.security.aop;

import com.serliunx.varytalk.common.annotation.PermissionRequired;
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
        Long userId = SecurityUtils.getUserId();
        PermissionRequired annotation = methodSignature.getMethod().getAnnotation(PermissionRequired.class);
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
