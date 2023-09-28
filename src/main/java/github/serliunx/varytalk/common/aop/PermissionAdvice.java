package github.serliunx.varytalk.common.aop;

import github.serliunx.varytalk.common.annotation.PermissionRequired;
import github.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import github.serliunx.varytalk.common.exception.ServiceException;
import github.serliunx.varytalk.common.util.RedisUtils;
import github.serliunx.varytalk.common.util.SecurityUtils;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.service.SystemUserService;
import github.serliunx.varytalk.security.service.PermissionService;
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
                            PermissionService permissionService,
                            SystemAutoConfigurer systemAutoConfigurer,
                            RedisUtils redisUtils) {

        this.systemUserService = systemUserService;
        this.permissionService = permissionService;
    }

    @Before("github.serliunx.varytalk.common.aop.PointCutDefinition.permissionPoint()")
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
