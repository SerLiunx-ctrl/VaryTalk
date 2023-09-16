package github.serliunx.varytalk.project.system.service;

import github.serliunx.varytalk.project.system.entity.SystemRolePermission;

import java.util.List;

public interface SystemRolePermissionService {
    List<SystemRolePermission> selectList(SystemRolePermission systemRolePermission);

    SystemRolePermission checkIfGiven(Long roleId, Long permissionId);

    List<SystemRolePermission> selectByRoleId(Long roleId);

    Long insertRolePermission(SystemRolePermission systemRolePermission);
}
