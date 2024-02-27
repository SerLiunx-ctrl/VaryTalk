package com.serliunx.varytalk.system.service;

/**
 * 验证码相关服务
 * @author SerLiunx
 * @since 1.0
 */
public interface CaptchaService {

    /**
     * 生成新的验证码
     * @param uuid Session ID
     * @return 验证码
     */
    String generateCode(String uuid);

    /**
     * 获取验证码
     * @param uuid Session ID
     * @return 验证码
     */
    String getCode(String uuid);

    /**
     * 删除验证码
     * @param uuid UUID
     */
    void deleteCode(String uuid);
}
