package com.serliunx.varytalk.system.event;

import com.serliunx.varytalk.system.entity.SystemPermission;
import lombok.Getter;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Getter
public class PermissionUpdateEvent {

    private final List<SystemPermission> permissions;

    public PermissionUpdateEvent(List<SystemPermission> permissions) {
        this.permissions = permissions;
    }
}
