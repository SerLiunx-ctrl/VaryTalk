package com.serliunx.varytalk.system.service.impl;

import com.serliunx.varytalk.framework.core.exception.PermissionNotFoundException;
import com.serliunx.varytalk.system.entity.SystemPermission;
import com.serliunx.varytalk.system.entity.SystemRolePermission;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.entity.SystemUserPermission;
import com.serliunx.varytalk.system.service.PermissionService;
import com.serliunx.varytalk.system.service.SystemPermissionService;
import com.serliunx.varytalk.system.service.SystemRolePermissionService;
import com.serliunx.varytalk.system.service.SystemUserPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final SystemPermissionService systemPermissionService;
    private final SystemUserPermissionService systemUserPermissionService;
    private final SystemRolePermissionService systemRolePermissionService;

    public PermissionServiceImpl(SystemPermissionService systemPermissionService,
                                 SystemUserPermissionService systemUserPermissionService,
                                 SystemRolePermissionService systemRolePermissionService) {
        this.systemPermissionService = systemPermissionService;
        this.systemUserPermissionService = systemUserPermissionService;
        this.systemRolePermissionService = systemRolePermissionService;
    }

    @Override
    public boolean hasRole(SystemUser systemUser, Long[] roleIds) {
        for (Long roleId : roleIds) {
            if(systemUser.getRoleId().equals(roleId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(SystemUser systemUser, String permission){
        List<SystemPermission> list = systemPermissionService.selectList();
        List<String> valueList = list
                .stream()
                .map(SystemPermission::getValue)
                .toList();

        if(valueList.isEmpty() || !valueList.contains(permission)){
            throw new PermissionNotFoundException(permission);
        }

        //第一步, 直接检查该用户是否拥有该节点, 拥有则直接返回真
        List<String> userPermissions = systemUserPermissionService
                .selectByUserId(systemUser.getId())
                .stream()
                .map(SystemUserPermission::getPermissionValue)
                .toList();
        if(userPermissions.contains(permission)){
            return true;
        }

        //第二步, 检查用户所属角色是否拥有该节点, 拥有则直接返回真
        List<String> rolePermissions = systemRolePermissionService
                .selectByRoleId(systemUser.getRoleId())
                .stream()
                .map(SystemRolePermission::getPermissionValue)
                .toList();
        if(rolePermissions.contains(permission)){
            return true;
        }

        //第三步, 匹配用户所属角色的权限节点,
        if(matchPermission(rolePermissions, permission)){
            return true;
        }

        //最后匹配用户节点
        return matchPermission(userPermissions, permission);
    }

    @Override
    public boolean matchPermission(List<String> values, String permission){
        String[] split = permission.split("\\.");
        start: for (String value : values) {
            String[] splitValue = value.split("\\.");
            if(splitValue.length == 0 || split.length == 0){
                throw new RuntimeException("权限节点分割错误, 如果你不知道发生了什么. 请联系维护人员");
            }
            int index = 0;
            while(index < splitValue.length && index < split.length){
                if(split[index].equals(splitValue[index])){
                    index++;
                }else if("*".equals(splitValue[index])){
                    return true;
                }else{
                    continue start;
                }
            }
        }
        return false;
    }
}
