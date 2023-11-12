package com.serliunx.varytalk.forum.service.impl;

import com.serliunx.varytalk.cache.annotation.Cache;
import com.serliunx.varytalk.cache.annotation.CacheRefresh;
import com.serliunx.varytalk.cache.annotation.TagEntity;
import com.serliunx.varytalk.cache.annotation.TagValue;
import com.serliunx.varytalk.common.annotation.SetOperator;
import com.serliunx.varytalk.forum.entity.ForumSection;
import com.serliunx.varytalk.forum.mapper.ForumSectionMapper;
import com.serliunx.varytalk.forum.service.ForumSectionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class ForumSectionServiceImpl implements ForumSectionService {

    private final ForumSectionMapper forumSectionMapper;

    public ForumSectionServiceImpl(ForumSectionMapper forumSectionMapper) {
        this.forumSectionMapper = forumSectionMapper;
    }

    @Override
    public List<ForumSection> selectList(ForumSection forumSection) {
        return forumSectionMapper.selectList(forumSection);
    }

    @Override
    @SetOperator(ForumSection.class)
    @CacheRefresh(method = "selectByCategoryId")
    public void insertForumSection(@TagEntity ForumSection forumSection) {
        forumSectionMapper.insertForumSection(forumSection);
    }

    @Override
    public ForumSection selectByName(String sectionName) {
        return forumSectionMapper.selectByName(sectionName);
    }

    @Override
    @Cache(time = 1, timeUnit = TimeUnit.DAYS)
    public List<ForumSection> selectByCategoryId(@TagValue("categoryId") Long categoryId) {
        return forumSectionMapper.selectByCategoryId(categoryId);
    }
}
