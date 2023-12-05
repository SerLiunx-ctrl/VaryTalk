package com.serliunx.varytalk.system.api;

import com.serliunx.varytalk.api.system.user.SystemUserApi;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.SystemUserService;
import org.springframework.stereotype.Component;

/**
 * 系统模块-用户接口实现
 * @author SerLiunx
 * @since 1.0
 */
@Component
public class SystemUserApiImpl implements SystemUserApi {

    private final SystemUserService systemUserService;

    public SystemUserApiImpl(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @Override
    public SystemUser selectUserByIdFlatted(Long id) {
        return systemUserService.selectUserByIdFlatted(id);
    }
}
