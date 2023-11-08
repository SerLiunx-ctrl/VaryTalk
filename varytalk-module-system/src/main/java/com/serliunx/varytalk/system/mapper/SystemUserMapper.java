package com.serliunx.varytalk.system.mapper;

import com.serliunx.varytalk.system.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemUserMapper {

    List<SystemUser> selectList(SystemUser systemUser);

    SystemUser checkUserByUsername(String username);

    SystemUser checkUserByPhoneNumber(String phoneNumber);

    SystemUser checkUserByEmail(String email);

    SystemUser selectUserById(Long id);

    SystemUser selectUserByIdFlatted(Long id);

    Long insertUser(SystemUser systemUser);

    void updateUser(SystemUser systemUser);

    void updateRole(SystemUser systemUser);

    void updatePassword(SystemUser systemUser);
}
