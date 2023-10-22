package com.serliunx.varytalk.system.mapper;

import com.serliunx.varytalk.system.entity.SystemRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemRolePermissionMapper {
    List<SystemRolePermission> selectList(SystemRolePermission systemRolePermission);

    SystemRolePermission checkIfGiven(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    List<SystemRolePermission> selectByRoleId(Long roleId);

    Long insertRolePermission(SystemRolePermission systemRolePermission);
}
