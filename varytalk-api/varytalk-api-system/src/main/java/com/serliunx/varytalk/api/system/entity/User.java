package com.serliunx.varytalk.api.system.entity;

/**
 * 系统模块-用户
 * @author SerLiunx
 * @since 1.0
 */
public interface User {

    Long getId();

    void setId(Long id);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    String getEmail();

    void setEmail(String email);

    String getNickName();

    void setNickName(String nickName);

    String getSex();

    void setSex(String sex);

    Long getRoleId();

    void setRoleId(Long roleId);

    Role getRole();

    void setRole(Role role);
}
