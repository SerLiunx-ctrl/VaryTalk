package com.serliunx.varytalk.system.service;


import com.serliunx.varytalk.system.entity.SystemPermission;

import java.util.List;

public interface SystemPermissionService {

    List<SystemPermission> selectList(SystemPermission systemPermission);

    /**
     * 获取系统所有权限, 用于权限校验
     * <li> 会优先从缓存中获取
     * @return 全部权限节点
     */
    List<SystemPermission> selectList();

    Long insertPermission(SystemPermission systemPermission);

    SystemPermission selectByValue(String value);

    SystemPermission selectByName(String nodeName);

    SystemPermission selectById(Long id);
}
