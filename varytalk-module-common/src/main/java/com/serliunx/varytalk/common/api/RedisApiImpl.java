package com.serliunx.varytalk.common.api;

import com.serliunx.varytalk.api.common.RedisApi;
import com.serliunx.varytalk.common.util.RedisUtils;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis API实现
 * @author SerLiunx
 * @since 1.0
 */
@Component
public class RedisApiImpl implements RedisApi {

    private final RedisUtils redisUtils;

    public RedisApiImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public boolean hasKey(String key) {
        return redisUtils.hasKey(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisUtils.keys(pattern);
    }

    @Override
    public Long getExpire(String key) {
        return redisUtils.getExpire(key);
    }

    @Override
    public void setExpire(String key, long time, TimeUnit timeUnit) {
        redisUtils.setExpire(key, time, timeUnit);
    }

    @Override
    public Long delete(String... key) {
        return redisUtils.delete(key);
    }

    @Override
    public Object get(String key) {
        return redisUtils.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return redisUtils.get(key, clazz);
    }

    @Override
    public void put(String key, Object value) {
        redisUtils.put(key, value);
    }

    @Override
    public void put(String key, Object value, long time) {
        redisUtils.put(key, value, time);
    }

    @Override
    public void put(String key, Object value, long time, TimeUnit timeUnit) {
        redisUtils.put(key, value, time, timeUnit);
    }

    @Override
    public Boolean setIfAbsent(String key, Object value, long time, TimeUnit timeUnit) {
        return redisUtils.setIfAbsent(key, value, time, timeUnit);
    }

    @Override
    public void putHashKeyValue(String key, String hashKey, Object value, long time, TimeUnit timeUnit) {
        redisUtils.putHashKeyValue(key, hashKey, value, time, timeUnit);
    }

    @Override
    public void putHashKeyValue(String key, String hashKey, Object value, long time) {
        redisUtils.putHashKeyValue(key, hashKey, value, time);
    }
}
