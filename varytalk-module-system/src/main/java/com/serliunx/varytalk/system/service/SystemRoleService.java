package com.serliunx.varytalk.system.service;

import com.serliunx.varytalk.system.entity.SystemRole;

import java.util.List;

public interface SystemRoleService {

    List<SystemRole> selectList(SystemRole systemRole);

    Long insertRole(SystemRole systemRole);

    SystemRole selectById(Long id);

    SystemRole selectByName(String roleName);
}
