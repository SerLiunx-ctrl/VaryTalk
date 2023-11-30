package com.serliunx.varytalk.system.interceptor.security;

import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.security.SecurityContext;
import com.serliunx.varytalk.security.SecurityInterceptor;
import com.serliunx.varytalk.security.annotation.CheckType;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.interceptor.group.PermissionCheckerGroup;
import com.serliunx.varytalk.system.service.PermissionService;
import com.serliunx.varytalk.system.service.SystemUserService;
import org.springframework.stereotype.Component;

/**
 * 安全检查拦截器 - 权限验证部分
 * @author SerLiunx
 * @since 1.0
 */
@Component
public class PermissionInterceptor implements SecurityInterceptor{

    private final SystemUserService systemUserService;
    private final PermissionService permissionService;

    public PermissionInterceptor(SystemUserService systemUserService,
                                 PermissionService permissionService) {
        this.systemUserService = systemUserService;
        this.permissionService = permissionService;
    }

    @Override
    public boolean preHandle(SecurityContext context) {
        if(!context.getType().equals(CheckType.VALUE)){
            return true;
        }
        String value = context.getValue();
        if(value == null || value.isEmpty()){
            return true;
        }
        Long userId = SecurityUtils.getUserId();
        SystemUser systemUser = systemUserService.selectUserById(userId);
        if(systemUser == null){
            throw new RuntimeException("指定用户不存在!");
        }
        return permissionService.hasPermission(systemUser, value);
    }

    @Override
    public String message() {
        return "权限不足!";
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Class<?> getGroup() {
        return PermissionCheckerGroup.class;
    }
}
