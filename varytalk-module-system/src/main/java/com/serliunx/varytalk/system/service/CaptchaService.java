package com.serliunx.varytalk.system.service;

/**
 * 验证码相关服务
 * @author SerLiunx
 * @since 1.0
 */
public interface CaptchaService {
    /**
     * 生成新的验证码
     * @param sessionId Session ID
     * @return 验证码
     */
    String generateCode(String sessionId);

    /**
     * 获取验证码
     * @param sessionId Session ID
     * @return 验证码
     */
    String getCode(String sessionId);

    /**
     * 删除验证码
     * @param sessionId Session ID
     */
    void deleteCode(String sessionId);
}
