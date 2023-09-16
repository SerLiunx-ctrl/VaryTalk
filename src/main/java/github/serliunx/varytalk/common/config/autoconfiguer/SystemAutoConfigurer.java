package github.serliunx.varytalk.common.config.autoconfiguer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("talk-system")
public class SystemAutoConfigurer {

    private final RedisPrefix redisPrefix = new RedisPrefix();

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

    public RedisPrefix getRedisPrefix() {
        return redisPrefix;
    }

    public String getAuthHeader() {
        return authHeader;
    }

    public void setAuthHeader(String authHeader) {
        this.authHeader = authHeader;
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
    }
}
