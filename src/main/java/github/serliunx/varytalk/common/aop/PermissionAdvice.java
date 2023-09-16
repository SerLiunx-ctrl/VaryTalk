package github.serliunx.varytalk.common.aop;

import github.serliunx.varytalk.common.annotation.PermissionRequired;
import github.serliunx.varytalk.common.exception.ServiceException;
import github.serliunx.varytalk.common.util.AopUtils;
import github.serliunx.varytalk.common.util.SecurityUtils;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.service.SystemUserPermissionService;
import github.serliunx.varytalk.project.system.service.SystemUserService;
import github.serliunx.varytalk.security.service.PermissionService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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

    @Before("github.serliunx.varytalk.common.aop.PointCutDefinition.permissionPoint()")
    public void permissionCheck(JoinPoint joinPoint){
        Long userId = SecurityUtils.getUserId();
        Method method = AopUtils.getMethodByJoinPoint(joinPoint);
        PermissionRequired annotation = method.getAnnotation(PermissionRequired.class);
        if(annotation == null){
            return;
        }
        SystemUser systemUser = systemUserService.selectUserById(userId);
        String permissionValue = annotation.value();
        boolean result = permissionService.hasPermission(systemUser, permissionValue);
        if(!result){
            throw new ServiceException("权限不足, 无法进行此操作!", 400);
        }
    }
}
