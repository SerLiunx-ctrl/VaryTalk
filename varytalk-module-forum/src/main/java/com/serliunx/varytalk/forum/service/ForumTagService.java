package com.serliunx.varytalk.forum.service;

import com.serliunx.varytalk.forum.entity.ForumTag;
import com.serliunx.varytalk.forum.entity.simple.ForumTagSimple;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
public interface ForumTagService {

    List<ForumTag> selectList(ForumTag forumTag);

    List<ForumTagSimple> selectListSimple();

    void insertForumTag(ForumTag forumTag);

    ForumTag selectById(Long tagId);

    ForumTag selectByName(String tagName);
}
