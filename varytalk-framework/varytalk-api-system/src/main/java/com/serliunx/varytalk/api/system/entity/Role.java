package com.serliunx.varytalk.api.system.entity;

/**
 * 系统模块-角色
 * @author SerLiunx
 * @since 1.0
 */
public interface Role {

    Long getId();

    void setId(Long id);

    Long getFatherId();

    void setFatherId(Long fatherId);

    String getRoleName();

    void setRoleName(String roleName);
}
