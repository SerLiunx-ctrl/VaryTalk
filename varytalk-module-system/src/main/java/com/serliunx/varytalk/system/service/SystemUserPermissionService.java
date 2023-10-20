package com.serliunx.varytalk.system.service;

import com.serliunx.varytalk.system.entity.SystemUserPermission;

import java.util.List;

public interface SystemUserPermissionService {

    List<SystemUserPermission> selectList(SystemUserPermission systemUserPermission);

    Long insertUserPermission(SystemUserPermission systemUserPermission);

    boolean checkIfGiven(Long userId, Long permissionId);

    List<SystemUserPermission> selectByUserId(Long userId);
}
