package com.serliunx.varytalk.framework.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("talk-system")
public class SystemAutoConfigurer {

    private RedisPrefix redisPrefix = new RedisPrefix();
    private FileInfo fileInfo = new FileInfo();
    private RedisTtl redisTtl = new RedisTtl();
    private HttpClient httpClient = new HttpClient();
    private Captcha captcha = new Captcha();

    /**
     * token密钥
     */
    private String tokenSecret = "serliunxpersonalblogtoken";

    /**
     * token头标识
     */
    private String authHeader = "Authorization";

    /**
     * token过期时间（小时为单位）
     */
    private Integer tokenExpireHour = 1;

    /**
     * 项目仓库所有者
     */
    private String owner;

    /**
     * 仓库名称
     */
    private String repos;

    /**
     * 百度地图开放平台AK
     */
    private String baiduAk;

    /**
     * 是否记录API接口日志(目前进记录访问耗时)
     */
    private Boolean apiLog;

    @Setter
    @Getter
    public static class RedisPrefix{
        /**
         * redis主键
         */
        private String mainPrefix = "vary_talk:";

        /**
         * 当前在线用户, 最终为 mainPrefix+onlineUsers 如: vary_talk:user_online:
         */
        private String onlineUsers = "user_online:";

        /**
         * 用户信息缓存, 用于鉴权、避免频繁查询数据库
         */
        private String userCache = "user_cache:";
    }

    @Setter
    @Getter
    public static class RedisTtl{
        /**
         * 用户信息缓存, 用于鉴权、避免频繁查询数据库
         */
        private Integer userCache = 6;
    }

    @Setter
    @Getter
    public static class FileInfo{
        /**
         * 文件上传路径
         */
        private String uploadPath = "C:/vary_talk/upload_files";
    }

    @Setter
    @Getter
    public static class HttpClient{
        /**
         * Http客户端扫描路径
         */
        private String basePackage = "com.serliunx.varytalk";
    }

    @Setter
    @Getter
    public static class Captcha{
        /**
         * 验证码字符
         */
        private String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        /**
         * 验证码长度
         */
        private Integer length = 6;
        /**
         * 验证码有效期(秒)
         */
        private Integer time = 300;
    }
}
