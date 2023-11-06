package com.serliunx.varytalk.configuration;

import com.serliunx.varytalk.system.entity.SystemPermission;
import com.serliunx.varytalk.system.service.SystemPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 系统初始化
 * @author SerLiunx
 * @since 1.0
 */
@Slf4j
public class SystemInitializer {

    private static final String STARTUP_PREFIX = "[初始化]";
    private final ApplicationContext applicationContext;

    public SystemInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void init(){
        //数据提前加载(缓存)
        refreshCache();
    }

    private void refreshCache(){
        //刷新权限节点缓存
        SystemPermissionService permissionService = applicationContext.getBean(SystemPermissionService.class);
        List<SystemPermission> systemPermissions = permissionService.selectList(null);
        log.info("{}权限节点共载入 {} 条数据.", STARTUP_PREFIX, systemPermissions.size());
    }
}
