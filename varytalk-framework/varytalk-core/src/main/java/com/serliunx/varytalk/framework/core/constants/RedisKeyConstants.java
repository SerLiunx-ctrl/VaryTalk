package com.serliunx.varytalk.framework.core.constants;

/**
 * redis keys
 * @author SerLiunx
 * @since 1.0
 */
public interface RedisKeyConstants {

    /**
     * 接口速率限制使用的key(分离模式)
     * <li> {prefix} - key的通用前缀, 从配置文件获取配置文件
     * <li> {ip_without_delimiter} - 没有小数点(.)的ip地址
     * <li> {uri} - 请求路径
     */
    String REDIS_KEY_RATE_LIMITER_SEPARATELY = "{prefix}rate_limiter:{ip_without_delimiter}:{uri}";

    /**
     * 接口速率限制使用的key(共用模式)
     * <li> {prefix} - key的通用前缀, 从配置文件获取配置文件
     * <li> {uri} - 请求路径
     */
    String REDIS_KEY_RATE_LIMITER_ALL = "{prefix}rate_limiter:{uri}";

    /**
     * 登录验证码存储
     * <li> {prefix} - key的通用前缀, 从配置文件获取配置文件
     * <li> {session_id} - Session ID
     */
    String REDIS_KEY_LOGIN_CAPTCHA = "{prefix}captcha_info:{session_id}";
}
