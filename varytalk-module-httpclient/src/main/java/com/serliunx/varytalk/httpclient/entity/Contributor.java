package com.serliunx.varytalk.httpclient.entity;

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

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("gravatar_id")
    private String gravatarId;

    private String url;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("followers_url")
    private String followersUrl;

    @JsonProperty("following_url")
    private String followingUrl;

    @JsonProperty("gists_url")
    private String gistsUrl;

    @JsonProperty("starred_url")
    private String starredUrl;

    @JsonProperty("subscriptions_url")
    private String subscriptionsUrl;

    @JsonProperty("organizations_url")
    private String organizationsUrl;

    @JsonProperty("repos_url")
    private String reposUrl;

    @JsonProperty("events_url")
    private String eventsUrl;

    @JsonProperty("received_events_url")
    private String received_eventsUrl;

    private String type;

    @JsonProperty("site_admin")
    private Boolean siteAdmin;

    private Integer contributions;
}
