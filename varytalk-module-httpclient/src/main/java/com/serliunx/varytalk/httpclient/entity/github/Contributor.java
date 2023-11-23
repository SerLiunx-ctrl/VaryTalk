package com.serliunx.varytalk.httpclient.entity.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 仓库贡献者实体
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class Contributor {

    private String login;

    private Integer id;

    private String nodeId;

    private String avatarUrl;

    private String gravatarId;

    private String url;

    private String htmlUrl;

    private String followersUrl;

    private String followingUrl;

    private String gistsUrl;

    private String starredUrl;

    private String subscriptionsUrl;

    private String organizationsUrl;

    private String reposUrl;

    private String eventsUrl;

    private String receivedEventsUrl;

    private String type;

    private Boolean siteAdmin;

    private Integer contributions;
}
