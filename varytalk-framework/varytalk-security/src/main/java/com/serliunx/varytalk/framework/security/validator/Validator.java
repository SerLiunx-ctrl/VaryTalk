package com.serliunx.varytalk.framework.security.validator;

import com.serliunx.varytalk.framework.security.support.ValidationContext;

/**
 * 校验器
 * @author SerLiunx
 * @since 1.0
 */
public interface Validator extends Order, Comparable<Validator>{

    /**
     * 主要的验证逻辑
     *
     * @param validationContext 验证上下文
     * @return 验证通过返回真、否则返回假.
     * <li> 未通过时将不会继续执行剩下的校验器(如果校验器模式为执行链模式)
     * @see ValidationContext
     */
    boolean preValidate(ValidationContext validationContext);

    /**
     * 校验器的分组
     * <li> 当校验器为独立验证器时将不会有任何效果
     */
    Class<?> group();

    /**
     * 校验器验证未通过时的提示语
     * @return 提示语
     */
    default String message(){
        return "校验器验证未通过!";
    }

    /**
     * 是否需要将该验证器加入到校验器链中
     * <li> 如果校验器属于独立校验器, 请重写该方法并返回false
     * <li> 未重写该方法则默认校验器需要加入到校验器链中
     * <li> 如果指定了校验器, 但对应的校验器非独立模式则会抛出异常
     */
    default boolean toChain(){
        return true;
    }

    @Override
    default int getOrder() {
        return 0;
    }

    @Override
    default int compareTo(Validator o) {
        if(getOrder() > o.getOrder()){
            return 1;
        }else if(getOrder() < o.getOrder()){
            return -1;
        }
        return 0;
    }
}
