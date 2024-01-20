package com.serliunx.varytalk.framework.security.support;

import com.serliunx.varytalk.framework.core.exception.ServiceException;
import com.serliunx.varytalk.framework.security.validator.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Slf4j
@SuppressWarnings("all")
public final class ValidationChainBuilder {

    public static LinkedList<Validator> VALIDATORS = new LinkedList<>();

    private ValidationChainBuilder(){}

    public static LinkedList<Validator> build(){
        if(VALIDATORS.isEmpty()){
            throw new ServiceException("校验器链未初始化完毕!", 400);
        }
        return VALIDATORS.stream()
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
