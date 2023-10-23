package com.serliunx.varytalk.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointCutDefinition {

    @Pointcut("@annotation(com.serliunx.varytalk.common.annotation.SetOperator)")
    public void operatorPoint(){}

    @Pointcut("@annotation(com.serliunx.varytalk.common.annotation.RequiredPermission)" +
            "|| @annotation(com.serliunx.varytalk.common.annotation.RequiredRole)")
    public void permissionPoint(){}

    @Pointcut("@annotation(com.serliunx.varytalk.common.annotation.Logger)")
    public void logPoint(){}
}
