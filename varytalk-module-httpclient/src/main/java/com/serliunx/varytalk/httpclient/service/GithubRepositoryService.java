package com.serliunx.varytalk.httpclient.service;

import com.serliunx.varytalk.common.util.RedisUtils;
import com.serliunx.varytalk.httpclient.client.GitHubRepositoryClient;
import com.serliunx.varytalk.httpclient.entity.Contributor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class GithubRepositoryService {

    private static final String KEY_CONTRIBUTOR = "varytalk:cache:github_contributors";

    private final GitHubRepositoryClient gitHubRepositoryClient;
    private final RedisUtils redisUtils;

    public GithubRepositoryService(GitHubRepositoryClient gitHubRepositoryClient,
                                   RedisUtils redisUtils) {
        this.gitHubRepositoryClient = gitHubRepositoryClient;
        this.redisUtils = redisUtils;
    }

    @SuppressWarnings("unchecked")
    public List<Contributor> getContributors(String owner, String repos){
        List<Contributor> contributors = (List<Contributor>)redisUtils.get(KEY_CONTRIBUTOR, List.class);
        if(contributors == null){
            contributors = gitHubRepositoryClient.getContributors(owner, repos);
            redisUtils.put(KEY_CONTRIBUTOR, contributors, 10, TimeUnit.MINUTES);
        }
        return contributors;
    }
}
