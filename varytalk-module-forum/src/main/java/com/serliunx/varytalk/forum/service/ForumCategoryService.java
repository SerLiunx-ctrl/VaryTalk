package com.serliunx.varytalk.forum.service;

import com.serliunx.varytalk.forum.entity.ForumCategory;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
public interface ForumCategoryService {

    List<ForumCategory> selectList(ForumCategory forumCategory);

    ForumCategory selectByName(String categoryName);

    Long insertForumCategory(ForumCategory forumCategory);
}
