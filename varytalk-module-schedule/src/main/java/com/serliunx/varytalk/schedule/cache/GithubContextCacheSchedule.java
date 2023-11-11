package com.serliunx.varytalk.schedule.cache;

import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.httpclient.entity.Contributor;
import com.serliunx.varytalk.httpclient.service.GithubRepositoryService;
import com.serliunx.varytalk.schedule.BaseSchedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * 请求GitHub相关数据的定时任务
 * @author SerLiunx
 * @since 1.0
 */
@Configuration
@Slf4j
public class GithubContextCacheSchedule extends BaseSchedule {

    private final GithubRepositoryService githubRepositoryService;

    public GithubContextCacheSchedule(SystemAutoConfigurer systemAutoConfigurer,
                                      GithubRepositoryService githubRepositoryService) {
        super(systemAutoConfigurer);
        this.githubRepositoryService = githubRepositoryService;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void runGetContributors(){
        log.info("[定时任务] 开始从 GitHub 拉取仓库的贡献者名单.");
        long timeStarted = System.currentTimeMillis();
        List<Contributor> contributors = githubRepositoryService.getContributors(systemAutoConfigurer.getOwner(),
                systemAutoConfigurer.getRepos(), true);
        log.info("[定时任务] 成功从 GitHub 拉取到了仓库的贡献者名单,共有 {} 名贡献者无私奉献! 拉取耗时大约 {} 毫秒.",
                contributors.size(), System.currentTimeMillis() - timeStarted);
    }
}
