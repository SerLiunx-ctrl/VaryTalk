package com.serliunx.varytalk.common.config.autoconfiguer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("talk-system")
public class SystemAutoConfigurer {

    private final RedisPrefix redisPrefix = new RedisPrefix();
    private final FileInfo fileInfo = new FileInfo();
    private final RedisTtl redisTtl = new RedisTtl();

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

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public Integer getTokenExpireHour() {
        return tokenExpireHour;
    }

    public void setTokenExpireHour(Integer tokenExpireHour) {
        this.tokenExpireHour = tokenExpireHour;
    }

    public String getAuthHeader() {
        return authHeader;
    }

    public void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
    }

    public RedisPrefix getRedisPrefix() {
        return redisPrefix;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public RedisTtl getRedisTtl() {
        return redisTtl;
    }

    public static class RedisPrefix{

        /**
         * redis主键
         */
        private String mainPrefix = "vary_talk:";

        /**
         * 当前在线用户, 最终为 mainPrefix+onlineUsers 如: vary_talk:user_online:
         */
        private String onlineUsers = mainPrefix + "user_online:";

        /**
         * 用户信息缓存, 用于鉴权、避免频繁查询数据库
         */
        private String userCache = mainPrefix +  "user_cache:";

        /**
         * #方法/属性缓存, 用于鉴权、日志记录
         */
        private String joinPointCache = mainPrefix + "join_point_cache:";

        public String getMainPrefix() {
            return mainPrefix;
        }

        public void setMainPrefix(String mainPrefix) {
            this.mainPrefix = mainPrefix;
        }

        public String getOnlineUsers() {
            return onlineUsers;
        }

        public void setOnlineUsers(String onlineUsers) {
            this.onlineUsers = this.mainPrefix + onlineUsers;
        }

        public String getJoinPointCache() {
            return joinPointCache;
        }

        public void setJoinPointCache(String joinPointCache) {
            this.joinPointCache = this.mainPrefix + joinPointCache;
        }

        public String getUserCache() {
            return userCache;
        }

        public void setUserCache(String userCache) {
            this.userCache = this.mainPrefix + userCache;
        }
    }

    public static class RedisTtl{

        /**
         * #方法/属性缓存, 用于鉴权、日志记录(存活时间)
         */
        private Integer joinPointCache = 24;

        /**
         * 用户信息缓存, 用于鉴权、避免频繁查询数据库
         */
        private Integer userCache = 6;

        public Integer getJoinPointCache() {
            return joinPointCache;
        }

        public void setJoinPointCache(Integer joinPointCache) {
            this.joinPointCache = joinPointCache;
        }

        public Integer getUserCache() {
            return userCache;
        }

        public void setUserCache(Integer userCache) {
            this.userCache = userCache;
        }
    }

    public static class FileInfo{

        /**
         * 文件上传路径
         */
        private String uploadPath = "C:/vary_talk/upload_files";

        public String getUploadPath() {
            return uploadPath;
        }

        public void setUploadPath(String uploadPath) {
            this.uploadPath = uploadPath;
        }
    }
}
