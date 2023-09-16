package github.serliunx.varytalk.project.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import github.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotNull;

@JsonPropertyOrder({"id", "roleId", "permissionId", "roleName", "permissionValue"})
public class SystemRolePermission extends BaseEntity {
    private Long id;

    @NotNull(message = "请指定角色!")
    private Long roleId;

    @NotNull(message = "请指定权限节点!")
    private Long permissionId;
    private String roleName;
    private String permissionValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }
}
