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

    @Bean
    public GitHubRepositoryClient gitHubRepositoryClient(){
        return GitHubRepositoryClient.build("https://api.github.com");
    }
}
