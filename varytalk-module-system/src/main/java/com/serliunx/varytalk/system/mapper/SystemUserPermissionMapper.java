package com.serliunx.varytalk.system.mapper;

import com.serliunx.varytalk.system.entity.SystemUserPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemUserPermissionMapper {

    List<SystemUserPermission> selectList(SystemUserPermission systemUserPermission);

    Long insertUserPermission(SystemUserPermission systemUserPermission);

    SystemUserPermission checkIfGiven(@Param("userId") Long userId, @Param("permissionId") Long permissionId);

    List<SystemUserPermission> selectByUserId(Long userId);
}
