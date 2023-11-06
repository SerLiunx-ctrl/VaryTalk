package com.serliunx.varytalk.system.service.impl;

import com.serliunx.varytalk.common.annotation.CacheRefresh;
import com.serliunx.varytalk.common.annotation.Cached;
import com.serliunx.varytalk.common.annotation.SetOperator;
import com.serliunx.varytalk.system.entity.SystemRolePermission;
import com.serliunx.varytalk.system.mapper.SystemRolePermissionMapper;
import com.serliunx.varytalk.system.service.SystemRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemRolePermissionServiceImpl implements SystemRolePermissionService {

    private final SystemRolePermissionMapper systemRolePermissionMapper;

    public SystemRolePermissionServiceImpl(SystemRolePermissionMapper systemRolePermissionMapper) {
        this.systemRolePermissionMapper = systemRolePermissionMapper;
    }

    @Override
    public List<SystemRolePermission> selectList(SystemRolePermission systemRolePermission) {
        return systemRolePermissionMapper.selectList(systemRolePermission);
    }

    @Override
    public boolean checkIfGiven(Long roleId, Long permissionId) {
        return systemRolePermissionMapper.checkIfGiven(roleId, permissionId) != null;
    }

    @Override
    @Cached(index = 0)
    public List<SystemRolePermission> selectByRoleId(Long roleId) {
        return systemRolePermissionMapper.selectByRoleId(roleId);
    }

    @Override
    @SetOperator(SystemRolePermission.class)
    @CacheRefresh(
            value = "selectByRoleId",
            clazz = SystemRolePermission.class,
            propertyName = "roleId"
    )
    public Long insertRolePermission(SystemRolePermission systemRolePermission) {
        return systemRolePermissionMapper.insertRolePermission(systemRolePermission);
    }
}
