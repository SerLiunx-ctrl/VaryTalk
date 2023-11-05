package com.serliunx.varytalk.httpclient.config;

import com.serliunx.varytalk.httpclient.client.GitHubRepositoryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置、bean注册
 * @author SerLiunx
 * @since 1.0
 */
@Configuration
public class HttpClientConfiguration {

    private static final String GITHUB_API_URL = "https://api.github.com";

    @Bean
    public GitHubRepositoryClient gitHubRepositoryClient(){
        return GitHubRepositoryClient.build(GITHUB_API_URL);
    }
}
