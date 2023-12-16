package com.serliunx.varytalk.schedule.service;

import com.serliunx.varytalk.framework.cache.annotation.Cache;
import com.serliunx.varytalk.schedule.client.GitHubRepositoryClient;
import com.serliunx.varytalk.schedule.entity.github.Contributor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class GithubRepositoryServiceImpl implements GithubRepositoryService{

    private final GitHubRepositoryClient gitHubRepositoryClient;

    public GithubRepositoryServiceImpl(GitHubRepositoryClient gitHubRepositoryClient) {
        this.gitHubRepositoryClient = gitHubRepositoryClient;
    }

    @Cache(time = 10)
    @Override
    public List<Contributor> getContributors(String owner, String repos){
        return gitHubRepositoryClient.getContributors(owner, repos);
    }

    @Cache(time = 10, forceRefresh = true)
    @Override
    public List<Contributor> getContributors(String owner, String repos, boolean refresh){
        return refresh ? gitHubRepositoryClient.getContributors(owner, repos) : getContributors(owner, repos);
    }
}
