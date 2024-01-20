package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.api.system.entity.Role;
import com.serliunx.varytalk.framework.core.entity.base.BaseEntity;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@JsonPropertyOrder({"id", "roleName"})
public class SystemRole extends BaseEntity implements Role {

    private Long id;

    /**
     * 父角色id
     */
    private Long fatherId;

    @Length(min = 2, message = "角色名称长度过短!")
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getFatherId() {
        return fatherId;
    }

    @Override
    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    @Override
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
