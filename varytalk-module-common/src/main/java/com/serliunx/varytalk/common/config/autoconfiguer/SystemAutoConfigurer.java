package com.serliunx.varytalk.common.config.autoconfiguer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("all")
@ConfigurationProperties("talk-system")
public class SystemAutoConfigurer {

    private RedisPrefix redisPrefix = new RedisPrefix();
    private FileInfo fileInfo = new FileInfo();
    private RedisTtl redisTtl = new RedisTtl();
    private HttpClient httpClient = new HttpClient();

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

    public String getBaiduAk() {
        return baiduAk;
    }

    public void setBaiduAk(String baiduAk) {
        this.baiduAk = baiduAk;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepos() {
        return repos;
    }

    public void setRepos(String repos) {
        this.repos = repos;
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

    public void setRedisPrefix(RedisPrefix redisPrefix) {
        this.redisPrefix = redisPrefix;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public void setRedisTtl(RedisTtl redisTtl) {
        this.redisTtl = redisTtl;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
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

        public String getUserCache() {
            return userCache;
        }

        public void setUserCache(String userCache) {
            this.userCache = this.mainPrefix + userCache;
        }
    }

    public static class RedisTtl{

        /**
         * 用户信息缓存, 用于鉴权、避免频繁查询数据库
         */
        private Integer userCache = 6;

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

    public static class HttpClient{

        private String basePackage = "com.serliunx.varytalk";

        public String getBasePackage() {
            return basePackage;
        }

        public void setBasePackage(String basePackage) {
            this.basePackage = basePackage;
        }
    }
}
