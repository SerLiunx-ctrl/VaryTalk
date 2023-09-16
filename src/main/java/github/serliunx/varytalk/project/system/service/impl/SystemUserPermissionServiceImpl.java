package github.serliunx.varytalk.project.system.service.impl;

import github.serliunx.varytalk.common.annotation.SetOperator;
import github.serliunx.varytalk.project.system.entity.SystemUserPermission;
import github.serliunx.varytalk.project.system.mapper.SystemUserPermissionMapper;
import github.serliunx.varytalk.project.system.service.SystemUserPermissionService;
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
    public Long insertUserPermission(SystemUserPermission systemUserPermission) {
        return systemUserPermissionMapper.insertUserPermission(systemUserPermission);
    }

    @Override
    public boolean checkIfGiven(Long userId, Long permissionId) {
        return systemUserPermissionMapper.checkIfGiven(userId, permissionId) != null;
    }

    @Override
    public List<SystemUserPermission> selectByUserId(Long userId) {
        return systemUserPermissionMapper.selectByUserId(userId);
    }
}
