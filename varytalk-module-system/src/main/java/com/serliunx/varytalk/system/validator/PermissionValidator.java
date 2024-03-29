package com.serliunx.varytalk.system.validator;

import com.serliunx.varytalk.framework.core.tool.SecurityUtils;
import com.serliunx.varytalk.framework.security.annotation.ApiValidator;
import com.serliunx.varytalk.framework.security.constant.ValidationType;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
import com.serliunx.varytalk.framework.security.support.ValidationContext;
import com.serliunx.varytalk.framework.security.validator.Validator;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.PermissionService;
import com.serliunx.varytalk.system.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;

/**
 * 权限节点值校验器
 * @author SerLiunx
 * @since 1.0
 */
@Slf4j
@ApiValidator
public class PermissionValidator implements Validator {

    private final SystemUserService systemUserService;
    private final PermissionService permissionService;

    public PermissionValidator(SystemUserService systemUserService,
                               PermissionService permissionService) {
        this.systemUserService = systemUserService;
        this.permissionService = permissionService;
    }

    @Override
    public boolean preValidate(ValidationContext context) {
        //只关注值类型的校验参数值
        if(context.getType() != ValidationType.VALUE){
            return true;
        }
        SystemUser systemUser = systemUserService.selectUserByIdFlatted(SecurityUtils.getUserId());
        return permissionService.hasPermission(systemUser, context.getValue());
    }

    @Override
    public Class<?> group() {
        return PermissionGroup.class;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public String message() {
        return "你无权这样做!";
    }
}
