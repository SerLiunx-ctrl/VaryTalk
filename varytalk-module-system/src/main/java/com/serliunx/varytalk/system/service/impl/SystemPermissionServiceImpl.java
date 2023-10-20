package com.serliunx.varytalk.system.service.impl;

import com.serliunx.varytalk.common.annotation.SetOperator;
import com.serliunx.varytalk.system.entity.SystemPermission;
import com.serliunx.varytalk.system.mapper.SystemPermissionMapper;
import com.serliunx.varytalk.system.service.SystemPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @SetOperator(SystemPermission.class)
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
