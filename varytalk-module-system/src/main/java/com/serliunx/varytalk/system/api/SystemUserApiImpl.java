package com.serliunx.varytalk.system.api;

import com.serliunx.varytalk.api.system.entity.User;
import com.serliunx.varytalk.api.system.user.SystemUserApi;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.PermissionService;
import com.serliunx.varytalk.system.service.SystemUserService;
import org.springframework.stereotype.Component;

/**
 * 系统模块-用户接口实现
 * @author SerLiunx
 * @since 1.0
 */
@Component
public class SystemUserApiImpl implements SystemUserApi {

    private final SystemUserService systemUserService;
    private final PermissionService permissionService;

    public SystemUserApiImpl(SystemUserService systemUserService,
                             PermissionService permissionService) {
        this.systemUserService = systemUserService;
        this.permissionService = permissionService;
    }

    @Override
    public SystemUser getUserByIdFlatted(Long id) {
        return systemUserService.selectUserByIdFlatted(id);
    }

    @Override
    public SystemUser getUserById(Long id) {
        return systemUserService.selectUserById(id);
    }

    @Override
    public boolean hasPermission(Long userId, String permission) {
        SystemUser systemUser = systemUserService.selectUserByIdFlatted(userId);
        if(systemUser == null){
            return false;
        }
        return permissionService.hasPermission(systemUser, permission);
    }
}
