package com.serliunx.varytalk.security.permission;

import com.serliunx.varytalk.common.annotation.RequiredPermission;
import com.serliunx.varytalk.common.annotation.PermitAll;
import com.serliunx.varytalk.common.annotation.RequiredRole;
import com.serliunx.varytalk.common.exception.AuthenticationConflictException;
import com.serliunx.varytalk.common.exception.ServiceException;
import com.serliunx.varytalk.common.util.ArrayUtils;
import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.security.service.PermissionService;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.SystemUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class PermissionAdvice {

    private final SystemUserService systemUserService;
    private final PermissionService permissionService;
    private final Logger logger = LoggerFactory.getLogger(PermissionAdvice.class);

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
        RequiredPermission requiredPermission = method.getAnnotation(RequiredPermission.class);
        RequiredRole requiredRole = method.getAnnotation(RequiredRole.class);
        SystemUser systemUser = null;
        if(requiredRole != null || requiredPermission != null){
            systemUser = systemUserService.selectUserById(userId);
        }
        boolean result = true;

        //判定角色
        if(requiredRole != null){
            String[] roleIds = requiredRole.value();
            Long[] convertedRoleIds = ArrayUtils.stringToLong(roleIds);
            result = permissionService.hasRole(systemUser, convertedRoleIds);
            logger.debug("权限检查-> 角色验证结果: 用户[{}], 角色{}, 结果: {}", systemUser.getUsername(),
                    Arrays.toString(roleIds), result ? "成功!" : "失败!");
        }

        //角色判定失败则判定权限
        if(!result){
            if(requiredPermission != null){
                String permission = requiredPermission.value();
                result = permissionService.hasPermission(systemUser, permission);
                logger.debug("权限检查-> 权限节点验证结果: 用户[{}], 权限节点[{}], 结果: {}", systemUser.getUsername(),
                        permission, result ? "成功!" : "失败!");
            }
        }
        if(!result){
            throw new ServiceException("权限不足, 无法进行此操作!", 400);
        }
    }
}
