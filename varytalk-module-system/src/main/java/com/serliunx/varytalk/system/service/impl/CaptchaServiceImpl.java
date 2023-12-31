package com.serliunx.varytalk.system.service.impl;

import com.serliunx.varytalk.common.constants.RedisKeyConstants;
import com.serliunx.varytalk.common.util.RedisUtils;
import com.serliunx.varytalk.common.util.StringUtils;
import com.serliunx.varytalk.framework.cache.annotation.Cache;
import com.serliunx.varytalk.system.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 验证码相关服务实现
 * @author SerLiunx
 * @since 1.0
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Value("${talk-system.captcha.characters}")
    private String characters;
    @Value("${talk-system.captcha.length}")
    private Integer length;
    @Value("${talk-system.captcha.time}")
    private Integer time;
    @Value("${talk-system.redis-prefix.main-prefix}")
    private String keyPrefix;

    private final RedisUtils redisUtils;

    public CaptchaServiceImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public String generateCode(String sessionId){
        StringBuilder captchaBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            captchaBuilder.append(characters.charAt(index));
        }
        String captcha = captchaBuilder.toString();
        redisUtils.put(StringUtils.applyPlaceholders(RedisKeyConstants.REDIS_KEY_LOGIN_CAPTCHA, keyPrefix, sessionId),
                captcha, (long) time, TimeUnit.SECONDS);
        log.debug("为客户端: {} 生成了新的验证码 -> {}", sessionId, captcha);
        return captcha;
    }

    @Override
    public String getCode(String sessionId){
        return (String)redisUtils.get(StringUtils.applyPlaceholders(RedisKeyConstants.REDIS_KEY_LOGIN_CAPTCHA,
                keyPrefix, sessionId));
    }

    @Override
    public void deleteCode(String sessionId){
        redisUtils.delete(StringUtils.applyPlaceholders(RedisKeyConstants.REDIS_KEY_LOGIN_CAPTCHA,
                keyPrefix, sessionId));
    }
}
