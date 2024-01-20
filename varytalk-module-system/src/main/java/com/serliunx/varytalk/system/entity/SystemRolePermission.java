package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.framework.core.entity.base.BaseEntity;
import com.serliunx.varytalk.framework.cache.annotation.TagValue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id", "roleId", "permissionId", "roleName", "permissionValue"})
public class SystemRolePermission extends BaseEntity {
    private Long id;

    @NotNull(message = "请指定角色!")
    @TagValue("roleId")
    private Long roleId;

    @NotNull(message = "请指定权限节点!")
    private Long permissionId;
    private String roleName;
    private String permissionValue;
}
