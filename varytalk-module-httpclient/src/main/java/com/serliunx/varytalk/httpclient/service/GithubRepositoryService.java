package com.serliunx.varytalk.httpclient.service;

import com.serliunx.varytalk.cache.annotation.Cache;
import com.serliunx.varytalk.cache.annotation.CacheRefresh;
import com.serliunx.varytalk.httpclient.client.GitHubRepositoryClient;
import com.serliunx.varytalk.httpclient.entity.Contributor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class GithubRepositoryService {

    private final GitHubRepositoryClient gitHubRepositoryClient;

    public GithubRepositoryService(GitHubRepositoryClient gitHubRepositoryClient) {
        this.gitHubRepositoryClient = gitHubRepositoryClient;
    }

    @Cache
    public List<Contributor> getContributors(String owner, String repos){
        return gitHubRepositoryClient.getContributors(owner, repos);
    }

    @CacheRefresh(method = "getContributors")
    public List<Contributor> updateContributors(String owner, String repository){
        return this.gitHubRepositoryClient.getContributors(owner, repository);
    }
}
