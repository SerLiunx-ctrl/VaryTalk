package com.serliunx.varytalk.system.service.impl;

import com.serliunx.varytalk.common.annotation.CacheRefresh;
import com.serliunx.varytalk.common.annotation.Cached;
import com.serliunx.varytalk.common.annotation.SetOperator;
import com.serliunx.varytalk.system.entity.SystemPermission;
import com.serliunx.varytalk.system.entity.SystemUserPermission;
import com.serliunx.varytalk.system.mapper.SystemUserPermissionMapper;
import com.serliunx.varytalk.system.service.SystemUserPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemUserPermissionServiceImpl implements SystemUserPermissionService {

    private final SystemUserPermissionMapper systemUserPermissionMapper;

    public SystemUserPermissionServiceImpl(SystemUserPermissionMapper systemUserPermissionMapper) {
        this.systemUserPermissionMapper = systemUserPermissionMapper;
    }

    @Override
    public List<SystemUserPermission> selectList(SystemUserPermission systemUserPermission) {
        return systemUserPermissionMapper.selectList(systemUserPermission);
    }

    @Override
    @SetOperator(SystemUserPermission.class)
    @CacheRefresh(
            value = "selectByUserId",
            clazz = SystemUserPermission.class,
            propertyName = "userId"
    )
    public Long insertUserPermission(SystemUserPermission systemUserPermission) {
        return systemUserPermissionMapper.insertUserPermission(systemUserPermission);
    }

    @Override
    public boolean checkIfGiven(Long userId, Long permissionId) {
        return systemUserPermissionMapper.checkIfGiven(userId, permissionId) != null;
    }

    @Override
    @Cached(index = 0)
    public List<SystemUserPermission> selectByUserId(Long userId) {
        return systemUserPermissionMapper.selectByUserId(userId);
    }
}
