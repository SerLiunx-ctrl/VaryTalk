package com.serliunx.varytalk.httpclient.client;

import com.serliunx.varytalk.httpclient.Builder;
import com.serliunx.varytalk.httpclient.entity.Contributor;
import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * GitHub 仓库相关接口客户端
 * @author SerLiunx
 * @since 1.0
 */
public interface GitHubRepositoryClient {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> getContributors(@Param("owner") String owner, @Param("repo") String repository);

    static GitHubRepositoryClient build(String url){
        return Builder.build(GitHubRepositoryClient.class, url);
    }
}
