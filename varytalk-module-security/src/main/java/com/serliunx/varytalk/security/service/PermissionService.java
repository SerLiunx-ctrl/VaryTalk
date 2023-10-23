package com.serliunx.varytalk.security.service;

import com.serliunx.varytalk.system.entity.SystemUser;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
public interface PermissionService {

    /**
     * 检查用户是否属于某个角色
     * @param systemUser 用户
     * @param roleIds 角色id
     * @return 属于返回真, 否则返回假
     */
    boolean hasRole(SystemUser systemUser, Long[] roleIds);

    /**
     * 检查用户是否拥有该权限
     * @param systemUser 用户
     * @param permission 权限
     * @return 拥有返回真, 否则返回假
     */
    boolean hasPermission(SystemUser systemUser, String permission);

    /**
     * 匹配权限集合和权限节点
     *
     * <p>
     * 匹配方式:
     * <li> system.* 匹配所有权限
     * <li> system.user.* 匹配所有用户相关权限(增删改查等)
     * <li> system.role.* 匹配所有角色相关权限(增删该查等)
     *
     * @param values 权限集合
     * @param permission 权限节点
     * @return 匹配返回真, 否则返回假
     */
    boolean matchPermission(List<String> values, String permission);
}
