package github.serliunx.varytalk.project.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import github.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotNull;

@JsonPropertyOrder({"id", "userId", "permissionId", "username", "permissionValue"})
public class SystemUserPermission extends BaseEntity {
    private Long id;

    @NotNull(message = "请指定用户!")
    private Long userId;

    @NotNull(message = "请指定权限节点!")
    private Long permissionId;
    private String username;
    private String permissionValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }
}
