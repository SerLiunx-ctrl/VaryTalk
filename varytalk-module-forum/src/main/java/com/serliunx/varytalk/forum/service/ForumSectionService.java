package com.serliunx.varytalk.forum.service;

import com.serliunx.varytalk.forum.entity.ForumSection;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
public interface ForumSectionService {
    List<ForumSection> selectList(ForumSection forumSection);

    void insertForumSection(ForumSection forumSection);

    ForumSection selectByName(String sectionName);

    List<ForumSection> selectByCategoryId(Long categoryId);
}
