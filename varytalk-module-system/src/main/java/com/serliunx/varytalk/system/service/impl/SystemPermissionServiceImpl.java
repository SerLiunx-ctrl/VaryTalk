package com.serliunx.varytalk.system.service.impl;

import com.serliunx.varytalk.framework.cache.annotation.Cache;
import com.serliunx.varytalk.framework.cache.annotation.CacheRefresh;
import com.serliunx.varytalk.framework.core.annotation.SetOperator;
import com.serliunx.varytalk.system.entity.SystemPermission;
import com.serliunx.varytalk.system.mapper.SystemPermissionMapper;
import com.serliunx.varytalk.system.service.SystemPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SystemPermissionServiceImpl implements SystemPermissionService {

    private final SystemPermissionMapper systemPermissionMapper;

    public SystemPermissionServiceImpl(SystemPermissionMapper systemPermissionMapper) {
        this.systemPermissionMapper = systemPermissionMapper;
    }

    @Override
    public List<SystemPermission> selectList(SystemPermission systemPermission) {
        return systemPermissionMapper.selectList(systemPermission);
    }

    @Override
    @Cache(time = 1, timeUnit = TimeUnit.HOURS)
    public List<SystemPermission> selectList() {
        return systemPermissionMapper.selectList(null);
    }

    @Override
    @SetOperator(SystemPermission.class)
    @CacheRefresh(method = "selectList")
    public Long insertPermission(SystemPermission systemPermission) {
        return systemPermissionMapper.insertPermission(systemPermission);
    }

    @Override
    public SystemPermission selectByValue(String value) {
        return systemPermissionMapper.selectByValue(value);
    }

    @Override
    public SystemPermission selectByName(String nodeName) {
        return systemPermissionMapper.selectByName(nodeName);
    }

    @Override
    public SystemPermission selectById(Long id) {
        return systemPermissionMapper.selectById(id);
    }
}
