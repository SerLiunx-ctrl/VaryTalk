package github.serliunx.varytalk.project.system.service;

import github.serliunx.varytalk.project.system.entity.SystemRole;

import java.util.List;

public interface SystemRoleService {

    List<SystemRole> selectList(SystemRole systemRole);

    Long insertRole(SystemRole systemRole);

    SystemRole selectById(Long id);

    SystemRole selectByName(String roleName);
}
