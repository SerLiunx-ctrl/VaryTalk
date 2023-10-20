package com.serliunx.varytalk.system.aop;

import com.serliunx.varytalk.common.annotation.SetOperator;
import com.serliunx.varytalk.common.util.AopUtils;
import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.SystemUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
public class OperatorAdvice {

    private final SystemUserService systemUserService;

    public OperatorAdvice(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @Before("com.serliunx.varytalk.common.aop.PointCutDefinition.operatorPoint()")
    public void dataOperator(JoinPoint joinPoint){
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            SetOperator annotation = methodSignature.getMethod().getAnnotation(SetOperator.class);
            if(annotation == null) return;

            SystemUser systemUser = systemUserService.selectUserById(SecurityUtils.getUserId());
            if(systemUser == null) return;

            //获取需修改的参数
            Object arg = AopUtils.getArg(joinPoint.getArgs(), annotation.value());
            if(arg == null) return;

            //获取参数中需要修改的属性, 设置为操作者的用户名称(非昵称)
            Field field = AopUtils.getField(arg.getClass(), annotation.fieldName(), true);
            if(field == null) return;
            field.setAccessible(true);
            field.set(arg, systemUser.getUsername());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
