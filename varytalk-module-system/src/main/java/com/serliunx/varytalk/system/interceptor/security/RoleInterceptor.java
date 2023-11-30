package com.serliunx.varytalk.system.interceptor.security;

import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.security.SecurityContext;
import com.serliunx.varytalk.security.SecurityInterceptor;
import com.serliunx.varytalk.security.annotation.CheckType;
import com.serliunx.varytalk.system.entity.SystemRole;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.interceptor.group.RoleCheckerGroup;
import com.serliunx.varytalk.system.service.PermissionService;
import com.serliunx.varytalk.system.service.SystemRoleService;
import com.serliunx.varytalk.system.service.SystemUserService;
import org.springframework.stereotype.Component;

/**
 * 安全检查拦截器 - 角色验证部分
 * <p>
 * <li> 多个角色请使用/分割, 这是默认匹配逻辑, 你可以在下方的preHandle方法中修改逻辑
 * @author SerLiunx
 * @since 1.0
 */
@Component
public class RoleInterceptor implements SecurityInterceptor {

    private final SystemUserService systemUserService;
    private final PermissionService permissionService;
    private final SystemRoleService systemRoleService;

    public RoleInterceptor(SystemUserService systemUserService,
                           PermissionService permissionService,
                           SystemRoleService systemRoleService) {
        this.systemUserService = systemUserService;
        this.permissionService = permissionService;
        this.systemRoleService = systemRoleService;
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
        SystemUser systemUser = systemUserService.selectUserById(SecurityUtils.getUserId());
        if(systemUser == null){
            throw new RuntimeException("指定用户不存在!");
        }
        Long[] roleIds;
        //检测是否存在分隔符
        if(value.contains("/")){
            String[] split = value.split("/");
            roleIds = new Long[split.length];
            for (int i = 0; i < split.length; i++) {
                roleIds[i] = Long.valueOf(split[i]);
            }
        }else{
            roleIds = new Long[]{Long.valueOf(value)};
        }
        Long roleId = SecurityUtils.getRoleId();
        SystemRole systemRole = systemRoleService.selectById(roleId);
        if(systemRole == null){
            throw new RuntimeException("指定角色不存在!");
        }
        return permissionService.hasRole(systemUser, roleIds);
    }

    @Override
    public String message() {
        return "你所属的角色不允许你这么做!";
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public Class<?> getGroup() {
        return RoleCheckerGroup.class;
    }
}
