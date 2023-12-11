package com.serliunx.varytalk.schedule.client;

import com.serliunx.varytalk.framework.httpclient.annotation.Client;
import com.serliunx.varytalk.schedule.entity.github.Contributor;
import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * GitHub 仓库相关接口客户端
 * @author SerLiunx
 * @since 1.0
 */
@Client(url = "https://api.github.com/repos")
public interface GitHubRepositoryClient {

    @RequestLine("GET /{owner}/{repo}/contributors")
    List<Contributor> getContributors(@Param("owner") String owner, @Param("repo") String repository);
}
