package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.common.annotation.PermitAll;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.common.result.CountResult;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.httpclient.service.GithubRepositoryService;
import com.serliunx.varytalk.system.service.SystemInformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SerLiunx
 * @since 1.0
 */
@RestController
@PermitAll
@RequestMapping("info")
public class SystemInformationController extends BaseController {

    private final SystemAutoConfigurer systemAutoConfigurer;
    private final SystemInformationService systemInformationService;
    private final GithubRepositoryService githubRepositoryService;

    public SystemInformationController(SystemAutoConfigurer systemAutoConfigurer,
                                       SystemInformationService systemInformationService,
                                       GithubRepositoryService githubRepositoryService) {
        this.systemAutoConfigurer = systemAutoConfigurer;
        this.systemInformationService = systemInformationService;
        this.githubRepositoryService = githubRepositoryService;
    }

    @GetMapping("contributors")
    @SuppressWarnings("all")
    public Result contributors(){
        return CountResult.success(githubRepositoryService.getContributors(systemAutoConfigurer.getOwner(),
                systemAutoConfigurer.getRepos()));
    }

    @GetMapping("system")
    public Result system(){
        return success(systemInformationService.getSystem());
    }
}
