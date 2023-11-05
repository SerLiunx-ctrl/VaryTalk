package com.serliunx.varytalk.forum.service.impl;

import com.serliunx.varytalk.forum.entity.ForumCategory;
import com.serliunx.varytalk.forum.mapper.ForumCategoryMapper;
import com.serliunx.varytalk.forum.service.ForumCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class ForumCategoryServiceImpl implements ForumCategoryService {

    private final ForumCategoryMapper forumCategoryMapper;

    public ForumCategoryServiceImpl(ForumCategoryMapper forumCategoryMapper) {
        this.forumCategoryMapper = forumCategoryMapper;
    }

    @Override
    public List<ForumCategory> selectList(ForumCategory forumCategory) {
        return forumCategoryMapper.selectList(forumCategory);
    }

    @Override
    public ForumCategory selectByName(String categoryName) {
        return forumCategoryMapper.selectByName(categoryName);
    }

    @Override
    public Long insertForumCategory(ForumCategory forumCategory) {
        return forumCategoryMapper.insertForumCategory(forumCategory);
    }
}
