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
    User selectUserByIdFlatted(Long id);
}
