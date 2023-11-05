package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.common.annotation.PermitAll;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.common.result.CountResult;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.httpclient.client.GitHubRepositoryClient;
import com.serliunx.varytalk.httpclient.entity.Contributor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@RestController
@RequestMapping("info")
public class SystemInformationController extends BaseController {

    private final GitHubRepositoryClient gitHubRepositoryClient;
    private final SystemAutoConfigurer systemAutoConfigurer;

    public SystemInformationController(GitHubRepositoryClient gitHubRepositoryClient,
                                       SystemAutoConfigurer systemAutoConfigurer) {
        this.gitHubRepositoryClient = gitHubRepositoryClient;
        this.systemAutoConfigurer = systemAutoConfigurer;
    }

    @PermitAll
    @GetMapping("contributors")
    public Result contributors(){
        List<Contributor> contributors = gitHubRepositoryClient.getContributors(systemAutoConfigurer.getOwner(),
                systemAutoConfigurer.getRepos());
        return CountResult.success(contributors);
    }
}
