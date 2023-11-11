package com.serliunx.varytalk.system.event.handler;

import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.common.executor.SyncTaskExecutor;
import com.serliunx.varytalk.common.util.RedisUtils;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.event.UserUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class UserEventHandler {

    private final SyncTaskExecutor syncTaskExecutor;
    private final RedisUtils redisUtils;
    private final SystemAutoConfigurer systemAutoConfigurer;

    public UserEventHandler(SyncTaskExecutor syncTaskExecutor,
                            SystemAutoConfigurer systemAutoConfigurer,
                            RedisUtils redisUtils) {
        this.syncTaskExecutor = syncTaskExecutor;
        this.redisUtils = redisUtils;
        this.systemAutoConfigurer = systemAutoConfigurer;
    }

    @EventListener
    public void handle(UserUpdateEvent event) {
        SystemUser user = event.getUser();
        String username = user.getUsername();
        syncTaskExecutor.submit(() -> redisUtils.put(systemAutoConfigurer.getRedisPrefix().getUserCache() + username,
                user, systemAutoConfigurer.getRedisTtl().getUserCache(), TimeUnit.HOURS));
    }
}
