package com.serliunx.varytalk.httpclient.client;

import com.serliunx.varytalk.httpclient.annotation.FeignClient;
import com.serliunx.varytalk.httpclient.entity.Contributor;
import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * GitHub 仓库相关接口客户端
 * @author SerLiunx
 * @since 1.0
 */
@FeignClient(url = "https://api.github.com")
public interface GitHubRepositoryClient {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> getContributors(@Param("owner") String owner, @Param("repo") String repository);
}
