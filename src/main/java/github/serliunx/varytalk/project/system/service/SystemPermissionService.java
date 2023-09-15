package github.serliunx.varytalk.project.system.service;

import github.serliunx.varytalk.project.system.entity.SystemPermission;

import java.util.List;

public interface SystemPermissionService {

    List<SystemPermission> selectList(SystemPermission systemPermission);

    Long insertPermission(SystemPermission systemPermission);

    SystemPermission selectByValue(String value);

    SystemPermission selectByName(String nodeName);

    SystemPermission selectById(Long id);
}
