package com.serliunx.varytalk.httpclient.service;

import com.serliunx.varytalk.common.annotation.Cached;
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

    @Cached
    public List<Contributor> getContributors(String owner, String repos){
        return gitHubRepositoryClient.getContributors(owner, repos);
    }
}
