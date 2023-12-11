package com.serliunx.varytalk.framework.security.processor;

import com.serliunx.varytalk.common.exception.ServiceException;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.annotation.ApiValidations;
import com.serliunx.varytalk.framework.security.exception.ModeException;
import com.serliunx.varytalk.framework.security.support.ValidationChainBuilder;
import com.serliunx.varytalk.framework.security.support.ValidationContext;
import com.serliunx.varytalk.framework.security.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Slf4j
@Aspect
@Component
public class ApiValidationProcessor implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Before("@annotation(com.serliunx.varytalk.framework.security.annotation.ApiValidation)")
    public void process(JoinPoint joinPoint){
        ApiValidation apiValidation = getApiValidation(joinPoint);
        process0(joinPoint, apiValidation);
    }

    @Before("@annotation(com.serliunx.varytalk.framework.security.annotation.ApiValidations)")
    public void processes(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        ApiValidations validations = signature.getMethod().getAnnotation(ApiValidations.class);
        ApiValidation[] validation = validations.value();
        for (ApiValidation apiValidation : validation) {
            process0(joinPoint, apiValidation);
        }
    }

    private void process0(JoinPoint joinPoint, ApiValidation validation){
        if(isChain(validation)){
            processChain(validation);
        }else{
            processSingle(joinPoint, validation);
        }
    }

    @Override
    @SuppressWarnings("all")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void
    processSingle(JoinPoint joinPoint, ApiValidation apiValidation){
        Validator validator = applicationContext.getBean(apiValidation.validator());
        if(validator.toChain()){
            String message = String.format("校验器模式冲突! %s 指定了校验器 %s, 但该校验器的模式为校验器链模式!",
                    getFullMethodName(joinPoint), apiValidation.validator().getName());
            throw new ModeException(message);
        }
        boolean b = validator.preValidate(buildContext(apiValidation));
        if(!b){
            throw new ServiceException(validator.message(), 400);
        }
    }

    private void processChain(ApiValidation apiValidation){
        LinkedList<Validator> validators = ValidationChainBuilder.VALIDATORS;
        ValidationContext context = buildContext(apiValidation);
        for (Validator validator : validators) {
            if(!inGroup(apiValidation.group(), validator.group())){
                continue;
            }
            boolean result = validator.preValidate(context);
            log.debug("校验器 {}, 结果 -> {}", validator.getClass(), result);
            if(!result){
                throw new ServiceException(validator.message(), 400);
            }
        }
    }

    /**
     * 验证一个校验器的组是否属于组集合
     * @param groups 组集合
     * @param group 组
     * @return 属于返回真, 否则返回假
     */
    private boolean inGroup(Class<?>[] groups, Class<?> group){
        return Arrays.asList(groups).contains(group);
    }

    /**
     * 依据注解信息构建校验上下文
     */
    private ValidationContext buildContext(ApiValidation apiValidation){
        Validator validator = isChain(apiValidation) ? null : applicationContext.getBean(apiValidation.validator());
        return new ValidationContext(apiValidation.value(), apiValidation.type(),
                apiValidation.valueType(), validator, apiValidation.group());
    }

    /**
     * 获取注解
     * @param joinPoint 切点
     * @return 注解
     */
    private ApiValidation getApiValidation(JoinPoint joinPoint){
        return getMethod(joinPoint).getAnnotation(ApiValidation.class);
    }

    /**
     * 获取方法签名
     * @param joinPoint 切点
     * @return 方法签名
     */
    private MethodSignature getSignature(JoinPoint joinPoint){
        return (MethodSignature)joinPoint.getSignature();
    }

    /**
     * 获取方法
     * @param joinPoint 切点
     * @return 方法
     */
    private Method getMethod(JoinPoint joinPoint){
        return getSignature(joinPoint).getMethod();
    }

    private boolean isChain(ApiValidation apiValidation){
       return apiValidation.validator().equals(Validator.class);
    }

    private String getFullMethodName(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getName();
        return className + "#" + getMethod(joinPoint).getName();
    }
}
