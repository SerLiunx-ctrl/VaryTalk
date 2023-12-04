package com.serliunx.varytalk.security.processor;

import com.serliunx.varytalk.security.annotation.ApiValidator;
import com.serliunx.varytalk.security.exception.ModeException;
import com.serliunx.varytalk.security.support.ValidationContext;
import com.serliunx.varytalk.security.validator.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Aspect
@Component
public class ApiValidatorProcessor implements ApplicationContextAware {

    private static final Class<? extends ApiValidator> VALIDATOR_CLASS = ApiValidator.class;
    private ApplicationContext applicationContext;

    @Before("@annotation(com.serliunx.varytalk.security.annotation.ApiValidator)")
    public void process(JoinPoint joinPoint){
        ApiValidator apiValidator = getApiValidator(joinPoint);
        if(!apiValidator.validator().equals(Validator.class)){
            processSingle(joinPoint);
        }else{
            processChain(joinPoint);
        }
    }

    private void processSingle(JoinPoint joinPoint){
        ApiValidator apiValidator = getApiValidator(joinPoint);
        Validator validator = applicationContext.getBean(apiValidator.validator());
        if(validator.toChain()){
//            String message = String.format("校验器模式冲突! %s 指定了校验器 %s, 但该校验器的模式为校验器链模式!");
            throw new ModeException("校验器模式冲突");
        }
        validator.preValidate(buildContext(joinPoint));
    }

    private void processChain(JoinPoint joinPoint){

    }

    /**
     * 依据注解信息构建校验上下文
     */
    private ValidationContext buildContext(JoinPoint joinPoint){
        ApiValidator apiValidator = getApiValidator(joinPoint);
        return new ValidationContext()
                .setValidator(applicationContext.getBean(apiValidator.validator()))
                .setType(apiValidator.type())
                .setValueType(apiValidator.valueType())
                .setValue(apiValidator.value());
    }

    private ApiValidator getApiValidator(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(VALIDATOR_CLASS);
    }

    @Override
    @SuppressWarnings("all")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
