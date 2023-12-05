package com.serliunx.varytalk.system.validator;

import com.serliunx.varytalk.security.annotation.ApiValidator;
import com.serliunx.varytalk.security.support.ValidationContext;
import com.serliunx.varytalk.security.validator.Validator;
import com.serliunx.varytalk.system.validator.group.PermissionGroup;

/**
 * 角色校验器
 * @author SerLiunx
 * @since 1.0
 */
@ApiValidator
public class RoleValidator implements Validator {

    @Override
    public boolean preValidate(ValidationContext validationContext) {
        return true;
    }

    @Override
    public Class<?> group() {
        return PermissionGroup.class;
    }
}
