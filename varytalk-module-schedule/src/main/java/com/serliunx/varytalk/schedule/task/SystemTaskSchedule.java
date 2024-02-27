package com.serliunx.varytalk.schedule.task;

import com.serliunx.varytalk.framework.core.config.SystemAutoConfigurer;
import com.serliunx.varytalk.schedule.constant.MessageStorage;
import com.serliunx.varytalk.schedule.entity.github.Contributor;
import com.serliunx.varytalk.schedule.service.GithubRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 系统内置定时任务
 * @author SerLiunx
 * @since 1.0
 */
@Slf4j
@Component
public class SystemTaskSchedule {

    private final GithubRepositoryService githubRepositoryService;
    private final SystemAutoConfigurer systemAutoConfigurer;

    public SystemTaskSchedule(GithubRepositoryService githubRepositoryService,
                              SystemAutoConfigurer systemAutoConfigurer) {
        this.githubRepositoryService = githubRepositoryService;
        this.systemAutoConfigurer = systemAutoConfigurer;
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    public void doGithubRepositoryContributorsTask(){
        List<Contributor> contributors = githubRepositoryService.getContributors(systemAutoConfigurer.getOwner(),
                systemAutoConfigurer.getRepos(), true);
        log.info("{} 成功载入 {} 个贡献者信息, 感谢他们的无私奉献!", MessageStorage.SCHEDULE_PREFIX, contributors.size());
    }
}
