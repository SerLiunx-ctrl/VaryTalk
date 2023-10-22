package com.serliunx.varytalk.system.service;

import com.serliunx.varytalk.system.entity.SystemRolePermission;

import java.util.List;

public interface SystemRolePermissionService {

    List<SystemRolePermission> selectList(SystemRolePermission systemRolePermission);

    boolean checkIfGiven(Long roleId, Long permissionId);

    List<SystemRolePermission> selectByRoleId(Long roleId);

    Long insertRolePermission(SystemRolePermission systemRolePermission);
}
