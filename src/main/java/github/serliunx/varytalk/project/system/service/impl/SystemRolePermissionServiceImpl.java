package github.serliunx.varytalk.project.system.service.impl;

import github.serliunx.varytalk.common.annotation.SetOperator;
import github.serliunx.varytalk.project.system.entity.SystemRolePermission;
import github.serliunx.varytalk.project.system.mapper.SystemRolePermissionMapper;
import github.serliunx.varytalk.project.system.service.SystemRolePermissionService;
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
    public SystemRolePermission checkIfGiven(Long roleId, Long permissionId) {
        return systemRolePermissionMapper.checkIfGiven(roleId, permissionId);
    }

    @Override
    public List<SystemRolePermission> selectByRoleId(Long roleId) {
        return systemRolePermissionMapper.selectByRoleId(roleId);
    }

    @Override
    @SetOperator(SystemRolePermission.class)
    public Long insertRolePermission(SystemRolePermission systemRolePermission) {
        return systemRolePermissionMapper.insertRolePermission(systemRolePermission);
    }
}
