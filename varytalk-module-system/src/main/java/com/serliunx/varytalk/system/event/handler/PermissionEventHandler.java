package com.serliunx.varytalk.system.event.handler;

import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.common.executor.SyncTaskExecutor;
import com.serliunx.varytalk.common.util.RedisUtils;
import com.serliunx.varytalk.system.event.PermissionUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Component
public class PermissionEventHandler {

    private final SyncTaskExecutor syncTaskExecutor;
    private final RedisUtils redisUtils;
    private final SystemAutoConfigurer systemAutoConfigurer;

    public PermissionEventHandler(SyncTaskExecutor syncTaskExecutor,
                                  RedisUtils redisUtils,
                                  SystemAutoConfigurer systemAutoConfigurer) {
        this.syncTaskExecutor = syncTaskExecutor;
        this.redisUtils = redisUtils;
        this.systemAutoConfigurer = systemAutoConfigurer;
    }

    @EventListener
    public void handlePermissionUpdateEvent(PermissionUpdateEvent event) {
        syncTaskExecutor.submit(() -> redisUtils.put(systemAutoConfigurer.getRedisPrefix().getPermissionsCache(),
                event.getPermissions(), systemAutoConfigurer.getRedisTtl().getPermissionsCache(), TimeUnit.HOURS));
    }
}
