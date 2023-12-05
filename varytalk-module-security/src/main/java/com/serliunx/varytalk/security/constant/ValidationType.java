package com.serliunx.varytalk.security.constant;

import com.serliunx.varytalk.security.annotation.ApiValidation;

/**
 * 验证器类型
 * <li> 由 {@link ApiValidation} 声明.
 * <li> 由 {@link com.serliunx.varytalk.security.validator.Validator} 的实现类处理.
 * <li> 注解的内容保存在{@link com.serliunx.varytalk.security.support.ValidationContext} 验证上下文中,
 * 并会在系统按照顺序执行验证器时传入该 "验证上下文"
 * @author SerLiunx
 * @since 1.0
 */
public enum ValidationType {
    VALUE,
}
