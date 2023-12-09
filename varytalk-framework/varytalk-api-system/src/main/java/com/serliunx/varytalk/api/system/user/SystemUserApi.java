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
}
