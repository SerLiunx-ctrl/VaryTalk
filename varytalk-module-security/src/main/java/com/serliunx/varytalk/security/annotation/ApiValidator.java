package com.serliunx.varytalk.security.annotation;

import com.serliunx.varytalk.security.constant.ValidationType;
import com.serliunx.varytalk.security.constant.ValidationValueType;
import com.serliunx.varytalk.security.validator.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口校验器(用于鉴权等操作)
 * @author SerLiunx
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiValidator {

    /**
     * 当校验器类型为{@link ValidationType}时生效
     * <li> 通过指定 {@link ValidationValueType} 来选择匹配的方式
     * <li> 默认支持: 全值匹配、SPEL、正则表达式等..
     */
    String value() default "";

    /**
     * 校验器类型
     * @see ValidationType
     */
    ValidationType type() default ValidationType.VALUE;

    /**
     * 当校验器类型为{@link ValidationType}时生效
     *
     * <li> 默认为{@link ValidationValueType#NORMAL} (全值匹配)
     * 等价于{@link String#equals(Object)}
     * @see ValidationValueType
     */
    ValidationValueType valueType() default ValidationValueType.NORMAL;

    /**
     * 手动指定校验器
     * <li> 在校验器为独立模式时生效
     */
    Class<? extends Validator> validator() default Validator.class;
}
