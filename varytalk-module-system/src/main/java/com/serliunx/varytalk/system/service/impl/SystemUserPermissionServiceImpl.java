package com.serliunx.varytalk.system.service.impl;

import com.serliunx.varytalk.framework.cache.annotation.Cache;
import com.serliunx.varytalk.framework.cache.annotation.CacheRefresh;
import com.serliunx.varytalk.framework.cache.annotation.TagEntity;
import com.serliunx.varytalk.framework.cache.annotation.TagValue;
import com.serliunx.varytalk.framework.core.annotation.SetOperator;
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
    @CacheRefresh(method = "selectByUserId")
    public Long insertUserPermission(@TagEntity SystemUserPermission systemUserPermission) {
        return systemUserPermissionMapper.insertUserPermission(systemUserPermission);
    }

    @Override
    public boolean checkIfGiven(Long userId, Long permissionId) {
        return systemUserPermissionMapper.checkIfGiven(userId, permissionId) != null;
    }

    @Override
    @Cache
    public List<SystemUserPermission> selectByUserId(@TagValue("userId") Long userId) {
        return systemUserPermissionMapper.selectByUserId(userId);
    }
}
