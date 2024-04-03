package com.serliunx.varytalk.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.serliunx.varytalk.framework.core.annotation.SetOperator;
import com.serliunx.varytalk.forum.entity.ForumSection;
import com.serliunx.varytalk.forum.mapper.ForumSectionMapper;
import com.serliunx.varytalk.forum.service.ForumSectionService;
import com.serliunx.varytalk.framework.cache.annotation.Cache;
import com.serliunx.varytalk.framework.cache.annotation.CacheRefresh;
import com.serliunx.varytalk.framework.cache.annotation.TagEntity;
import com.serliunx.varytalk.framework.cache.annotation.TagValue;
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
        return forumSectionMapper.selecSectiontList(forumSection);
    }

    @Override
    @SetOperator(ForumSection.class)
    @CacheRefresh(method = {"selectByCategoryId", "selectByCategoryIds"})
    public void insertForumSection(@TagEntity(ignore = {"selectByCategoryIds"}) ForumSection forumSection) {
        forumSectionMapper.insertForumSection(forumSection);
    }

    @Override
    public ForumSection selectByName(@TagValue("sectionName") String sectionName) {
        return forumSectionMapper.selectByName(sectionName);
    }

    @Override
    @Cache(time = 1, timeUnit = TimeUnit.DAYS)
    public List<ForumSection> selectByCategoryId(@TagValue("categoryId") Long categoryId) {
        return forumSectionMapper.selectByCategoryId(categoryId);
    }

    @Override
    @Cache
    public List<ForumSection> selectByCategoryIds(List<Long> categoryIds) {
        return forumSectionMapper.selectList(new LambdaQueryWrapper<>(ForumSection.class)
                .in(ForumSection::getCategoryId, categoryIds)
        );
    }
}
