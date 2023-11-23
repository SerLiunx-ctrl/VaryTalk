package com.serliunx.varytalk.httpclient.service;

import com.serliunx.varytalk.cache.annotation.Cache;
import com.serliunx.varytalk.httpclient.client.GitHubRepositoryClient;
import com.serliunx.varytalk.httpclient.entity.github.Contributor;
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

    @Cache(time = 10)
    public List<Contributor> getContributors(String owner, String repos){
        return gitHubRepositoryClient.getContributors(owner, repos);
    }

    @Cache(time = 10, forceRefresh = true)
    public List<Contributor> getContributors(String owner, String repos, boolean refresh){
        return refresh ? gitHubRepositoryClient.getContributors(owner, repos) : getContributors(owner, repos);
    }
}
