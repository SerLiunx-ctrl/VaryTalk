package com.serliunx.varytalk.common.interceptor;

import com.serliunx.varytalk.common.annotation.RateLimiter;
import com.serliunx.varytalk.common.exception.ServiceException;
import com.serliunx.varytalk.common.util.RedisUtils;
import com.serliunx.varytalk.common.util.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 访问速率控制
 * @author SerLiunx
 * @since 1.0
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RedisUtils redisUtils;

    private static final String LIMITER_PREFIX = "rate_limiter";

    @Value("${talk-system.redis-prefix.main-prefix}")
    private String keyPrefix;

    public RateLimitInterceptor(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod handlerMethod)){
            return false;
        }
        Method method = handlerMethod.getMethod();
        RateLimiter methodLimiter = method.getAnnotation(RateLimiter.class);
        if(methodLimiter == null){
            return true;
        }
        String ip = ServletUtils.getIp();
        String uri = ServletUtils.getRequestURI();
        String key = keyPrefix + LIMITER_PREFIX + ":" + ip.replace(".", "") + ":" + uri;
        Integer count = redisUtils.get(key, Integer.class);
        if(count != null && count >= methodLimiter.count()){
            throw new ServiceException("访问频率过高, 请稍后再试!", 400);
        }
        int newCount = count == null ? 1 : count + 1;
        redisUtils.put(key, newCount, methodLimiter.time(), TimeUnit.SECONDS);
        return true;
    }
}
