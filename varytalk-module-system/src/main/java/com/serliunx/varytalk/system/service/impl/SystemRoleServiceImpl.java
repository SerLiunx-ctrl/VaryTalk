package com.serliunx.varytalk.system.service.impl;

import com.serliunx.varytalk.common.annotation.SetOperator;
import com.serliunx.varytalk.system.entity.SystemRole;
import com.serliunx.varytalk.system.mapper.SystemRoleMapper;
import com.serliunx.varytalk.system.service.SystemRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    private final SystemRoleMapper systemRoleMapper;

    public SystemRoleServiceImpl(SystemRoleMapper systemRoleMapper) {
        this.systemRoleMapper = systemRoleMapper;
    }

    @Override
    public List<SystemRole> selectList(SystemRole systemRole) {
        return systemRoleMapper.selectList(systemRole);
    }

    @Override
    @SetOperator(SystemRole.class)
    public Long insertRole(SystemRole systemRole) {
        return systemRoleMapper.insertRole(systemRole);
    }

    @Override
    public SystemRole selectById(Long id) {
        return systemRoleMapper.selectById(id);
    }

    @Override
    public SystemRole selectByName(String roleName) {
        return systemRoleMapper.selectByName(roleName);
    }
}
