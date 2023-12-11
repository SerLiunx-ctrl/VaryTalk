package com.serliunx.varytalk.api.system.user;

import com.serliunx.varytalk.api.system.entity.User;

/**
 * 用户相关接口
 * @author SerLiunx
 * @since 1.0
 */
public interface SystemUserApi {

    /**
     * 根据id选择用户, 不包含角色等附加信息
     */
    User getUserByIdFlatted(Long id);

    /**
     * 根据id选择用户
     * @param id 用户id
     * @return 用户
     */
    User getUserById(Long id);

    /**
     * 检查用于是否拥有指定权限
     * @param userId 用户ID
     * @param permission 权限节点
     * @return 有该权限返回真、否则返回假
     */
    boolean hasPermission(Long userId, String permission);
}
