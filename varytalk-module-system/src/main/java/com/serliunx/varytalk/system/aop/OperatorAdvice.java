package com.serliunx.varytalk.system.aop;

import com.serliunx.varytalk.common.annotation.SetOperator;
import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.common.util.AopUtils;
import com.serliunx.varytalk.common.util.RedisUtils;
import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.SystemUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class OperatorAdvice {

    private final SystemUserService systemUserService;
    private final RedisUtils redisUtils;
    private final SystemAutoConfigurer systemAutoConfigurer;
    private final Map<String, Field> fieldMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(OperatorAdvice.class);

    public OperatorAdvice(SystemUserService systemUserService,
                          RedisUtils redisUtils,
                          SystemAutoConfigurer systemAutoConfigurer) {

        this.systemUserService = systemUserService;
        this.redisUtils = redisUtils;
        this.systemAutoConfigurer = systemAutoConfigurer;
    }

    @Before("com.serliunx.varytalk.common.aop.PointCutDefinition.operatorPoint()")
    public void dataOperator(JoinPoint joinPoint){
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            SetOperator annotation = methodSignature.getMethod().getAnnotation(SetOperator.class);
            if(annotation == null) return;

            //先从缓存中读取用户信息
            SystemUser cachedUser = redisUtils.get(systemAutoConfigurer.getRedisPrefix().getUserCache()
                    + SecurityUtils.getUsername(), SystemUser.class);

            SystemUser systemUser = cachedUser == null ? systemUserService.selectUserById(SecurityUtils.getUserId())
                    : cachedUser;
            if(systemUser == null) return;

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
                this.fieldMap.put(key, field);
                logger.debug("新增了一个字段缓存 -> {}", field.getName());
            }
            //获取参数中需要修改的属性, 设置为操作者的用户名称(非昵称)
            field.setAccessible(true);
            field.set(arg, systemUser.getUsername());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
