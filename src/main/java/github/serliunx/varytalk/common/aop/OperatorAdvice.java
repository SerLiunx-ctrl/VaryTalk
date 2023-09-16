package github.serliunx.varytalk.common.aop;

import github.serliunx.varytalk.common.annotation.GetOperator;
import github.serliunx.varytalk.common.exception.ServiceException;
import github.serliunx.varytalk.common.util.AopUtils;
import github.serliunx.varytalk.common.util.SecurityUtils;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.service.SystemUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Component
public class OperatorAdvice {

    private final SystemUserService systemUserService;

    public OperatorAdvice(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @Before("github.serliunx.varytalk.common.aop.PointCutDefinition.operatorPoint()")
    public void dataOperator(JoinPoint joinPoint){
        try {
            Method pointMethod = AopUtils.getMethodByJoinPoint(joinPoint);
            pointMethod.setAccessible(true);

            GetOperator annotation = pointMethod.getAnnotation(GetOperator.class);
            if(annotation == null) return;

            SystemUser systemUser = systemUserService.selectUserById(SecurityUtils.getUserId());
            if(systemUser == null) return;

            Object arg = AopUtils.getArg(joinPoint.getArgs(), annotation.value());
            if(arg == null) return;

            Field field = AopUtils.getField(arg.getClass(), annotation.fieldName(), true);
            if(field == null) return;

            field.setAccessible(true);
            field.set(arg, systemUser.getUsername());
        }catch (Exception e){
            throw new ServiceException(e.getClass() + ": " + e.getMessage(), 400);
        }
    }
}
