package com.serliunx.varytalk.system.mapper;

import com.serliunx.varytalk.system.entity.SystemRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemRoleMapper {
    List<SystemRole> selectList(SystemRole systemRole);

    Long insertRole(SystemRole systemRole);

    SystemRole selectById(Long id);

    SystemRole selectByName(String roleName);
}
