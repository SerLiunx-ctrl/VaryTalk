package github.serliunx.varytalk.common.config;

import github.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import github.serliunx.varytalk.common.util.RedisUtils;
import github.serliunx.varytalk.project.system.entity.SystemPermission;
import github.serliunx.varytalk.project.system.service.SystemPermissionService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 缓存配置类(主要将系统内量大且访问频率较高的数据放入缓存中)
 */
@Component
public class StartupCacheConfiguration {
    private final SystemPermissionService systemPermissionService;
    private final RedisUtils redisUtils;
    private final SystemAutoConfigurer systemAutoConfigurer;

    public StartupCacheConfiguration(SystemPermissionService systemPermissionService,
                                     RedisUtils redisUtils,
                                     SystemAutoConfigurer systemAutoConfigurer) {
        this.systemPermissionService = systemPermissionService;
        this.redisUtils = redisUtils;
        this.systemAutoConfigurer = systemAutoConfigurer;
        //初始化
        init();
    }
    private void init(){

    }
}
