package com.serliunx.varytalk.framework.core.interceptor;

import com.serliunx.varytalk.framework.core.annotation.RateLimiter;
import com.serliunx.varytalk.framework.core.constants.RedisKeyConstants;
import com.serliunx.varytalk.framework.core.exception.ServiceException;
import com.serliunx.varytalk.framework.core.tool.RedisUtils;
import com.serliunx.varytalk.framework.core.tool.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static com.serliunx.varytalk.framework.core.tool.StringUtils.applyPlaceholders;

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
        String key;

        //根据限制类型生成不同的key
        if(methodLimiter.type().equals(RateLimiter.LimitType.SEPARATELY)){
            key = applyPlaceholders(RedisKeyConstants.REDIS_KEY_RATE_LIMITER_SEPARATELY, keyPrefix,
                    ip.replace(".", ""), uri);
        }else {
            key = keyPrefix + LIMITER_PREFIX + ":" + uri;
            key = applyPlaceholders(RedisKeyConstants.REDIS_KEY_RATE_LIMITER_ALL, keyPrefix, uri);
        }

        Integer count = redisUtils.get(key, Integer.class);
        if(count != null && count >= methodLimiter.count()){
            throw new ServiceException("访问频率过高, 请稍后再试!", 400);
        }
        int newCount = count == null ? 1 : count + 1;
        redisUtils.put(key, newCount, methodLimiter.time(), TimeUnit.SECONDS);
        return true;
    }
}
