package com.serliunx.varytalk.forum.enums;

import com.serliunx.varytalk.common.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    BAN_USER(2, "拉黑用户"),
    UNBAN_USER(3, "将用户从黑名单中移除"),
    EDIT_GROUP(4, "编辑群组信息"),
    POST_THREAD(5, "在群组内发布帖子"),
    DELETE_THREAD(6, "删除群组内的帖子"),
    EDIT_THREAD(7, "编辑群组内的帖子(他人发布的帖子)"),
    LOCK_THREAD(8, "锁定群组内的帖子(无法编辑、回复)"),
    HIDE_THREAD(9, "隐藏群组内的帖子(非作者无法查看、回复)"),
    ;

    private final int code;
    private final String description;

    /**
     * 根据权限码获取具体权限信息
     * @param code 权限码
     * @return 具体权限
     */
    public static ForumGroupRolesPermission match(int code){
        return Arrays.stream(ForumGroupRolesPermission.values())
                .filter(f -> f.code == code)
                .findFirst()
                .orElseThrow();
    }

    /**
     * 从权限列表字符串中匹配具体的权限
     * @param permissions 权限列表字符串
     * @return 具体权限
     */
    public static List<ForumGroupRolesPermission> matchList(String permissions){
        return Arrays.stream(permissions.split(","))
                .map(s -> {
                    try {
                        return match(Integer.parseInt(s));
                    }catch (Exception e){
                        throw new ServiceException("群组权限解析错误, 请联系管理员!", 400);
                    }
                })
                .collect(Collectors.toList());
    }
}
