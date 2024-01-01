package com.serliunx.varytalk.system.aop;

import com.serliunx.varytalk.common.annotation.SetOperator;
import com.serliunx.varytalk.common.util.AopUtils;
import com.serliunx.varytalk.common.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class OperatorAdvice {

    private final Map<String, Field> fieldMap = new HashMap<>();

    @Before("@annotation(com.serliunx.varytalk.common.annotation.SetOperator)")
    public void dataOperator(JoinPoint joinPoint){
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            SetOperator annotation = methodSignature.getMethod().getAnnotation(SetOperator.class);
            if(annotation == null) return;

            //获取需修改的参数
            Object arg = AopUtils.getArg(joinPoint.getArgs(), annotation.value());
            if(arg == null) return;

            //从内存中查询中包含了该字段,减少反射的次数
            String key = arg.getClass().getName() + ":" + annotation.fieldName();
            Field field;
            field = fieldMap.get(key);
            if(field == null){
                field = AopUtils.getField(arg.getClass(), annotation.fieldName(), true);
                if(field == null) return;
                field.setAccessible(true);
                this.fieldMap.put(key, field);
                log.debug("新增了一个字段缓存 -> {}", field.getName());
            }
            //获取参数中需要修改的属性, 设置为操作者的用户名称(非昵称)
            field.set(arg, SecurityUtils.getUsername());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
