package com.serliunx.varytalk.forum.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 群组角色的权限列表
 * @author SerLiunx
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum ForumGroupRolesPermission {
    INVITE_USER(0, "邀请新用户"),
    KICK_USER(1, "踢出用户"),

    ;

    private final int code;
    private final String description;
}
