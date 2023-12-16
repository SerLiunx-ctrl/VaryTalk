package com.serliunx.varytalk.schedule.service;

import com.serliunx.varytalk.schedule.entity.github.Contributor;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
public interface GithubRepositoryService {

    List<Contributor> getContributors(String owner, String repos);

    List<Contributor> getContributors(String owner, String repos, boolean refresh);
}
