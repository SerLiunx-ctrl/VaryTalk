package com.serliunx.varytalk.configuration;

import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.httpclient.entity.github.Contributor;
import com.serliunx.varytalk.httpclient.service.GithubRepositoryService;
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
    private SystemAutoConfigurer systemAutoConfigurer;

    public SystemInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void init(){
        log.info("{}系统开始初始化...", STARTUP_PREFIX);
        long timeStarted = System.currentTimeMillis();
        this.systemAutoConfigurer = applicationContext.getBean(SystemAutoConfigurer.class);
        //数据提前加载(缓存)
        refreshPermissionsCache();
        refreshContributors();
        log.info("{}初始化完成, 共计耗时 {} 毫秒.", STARTUP_PREFIX, System.currentTimeMillis() - timeStarted);
    }

    private void refreshPermissionsCache(){
        //刷新权限节点缓存
        SystemPermissionService permissionService = applicationContext.getBean(SystemPermissionService.class);
        List<SystemPermission> systemPermissions = permissionService.selectList();
        log.info("{}权限节点共载入 {} 条数据.", STARTUP_PREFIX, systemPermissions.size());
    }

    private void refreshContributors(){
        GithubRepositoryService repositoryService = applicationContext.getBean(GithubRepositoryService.class);
        List<Contributor> contributors = repositoryService.getContributors(systemAutoConfigurer.getOwner(),
                systemAutoConfigurer.getRepos(), true);
        log.info("{}仓库贡献者共载入 {} 条数据.", STARTUP_PREFIX, contributors.size());
    }
}
