package com.serliunx.varytalk.forum.service;

import com.serliunx.varytalk.forum.entity.ForumGroup;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
public interface ForumGroupService {
    List<ForumGroup> selectList(ForumGroup forumGroup);

    ForumGroup selectById(Long groupId);

    ForumGroup selectByOwnerId(Long ownerId);

    ForumGroup selectByName(String groupName);

    ForumGroup selectByTag(String groupTag);

    void insertForumGroup(ForumGroup forumGroup);
}
