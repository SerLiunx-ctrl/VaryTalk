package github.serliunx.varytalk.common.config.autoconfiguer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("talk-system")
public class SystemAutoConfigurer {

    /**
     * token密钥
     */
    private String tokenSecret = "serliunxpersonalblogtoken";

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
}
