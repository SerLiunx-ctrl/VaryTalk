package com.serliunx.varytalk.forum.service.impl;

import com.serliunx.varytalk.framework.cache.annotation.Cache;
import com.serliunx.varytalk.framework.cache.annotation.CacheRefresh;
import com.serliunx.varytalk.framework.core.annotation.SetOperator;
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

    @Cache(time = 30)
    @Override
    public List<ForumCategory> selectSimpleList() {
        return forumCategoryMapper.selectSimpleList();
    }

    @Override
    public ForumCategory selectByName(String categoryName) {
        return forumCategoryMapper.selectByName(categoryName);
    }

    @Override
    public ForumCategory selectById(Long id) {
        return forumCategoryMapper.selectById(id);
    }

    @Override
    @CacheRefresh(method = "selectSimpleList")
    @SetOperator(value = ForumCategory.class)
    public Long insertForumCategory(ForumCategory forumCategory) {
        return forumCategoryMapper.insertForumCategory(forumCategory);
    }
}
