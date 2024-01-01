package com.serliunx.varytalk.forum.validator;

import com.serliunx.varytalk.framework.security.annotation.ApiValidator;
import com.serliunx.varytalk.framework.security.support.ValidationContext;
import com.serliunx.varytalk.framework.security.validator.Validator;

/**
 * 群组权限校验器
 * <li> 用户在群组中拥有不同角色(与系统角色区分开)
 * <li> 此校验器可以检测群组中的某用户是否允许执行某个操作
 * @author SerLiunx
 * @since 1.0
 */
@ApiValidator
public class ForumGroupPermissionValidator implements Validator{

    @Override
    public boolean preValidate(ValidationContext validationContext) {
        return true;
    }

    @Override
    public Class<?> group() {
        return null;
    }
}
