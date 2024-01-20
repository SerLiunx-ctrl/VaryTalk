package com.serliunx.varytalk.forum.service.impl;

import com.serliunx.varytalk.framework.core.annotation.SetOperator;
import com.serliunx.varytalk.forum.entity.ForumTag;
import com.serliunx.varytalk.forum.entity.simple.ForumTagSimple;
import com.serliunx.varytalk.forum.mapper.ForumTagMapper;
import com.serliunx.varytalk.forum.service.ForumTagService;
import com.serliunx.varytalk.framework.cache.annotation.Cache;
import com.serliunx.varytalk.framework.cache.annotation.CacheRefresh;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class ForumTagServiceImpl implements ForumTagService {

    private final ForumTagMapper forumTagMapper;

    public ForumTagServiceImpl(ForumTagMapper forumTagMapper) {
        this.forumTagMapper = forumTagMapper;
    }

    @Override
    public List<ForumTag> selectList(ForumTag forumTag) {
        return forumTagMapper.selectList(forumTag);
    }

    @Override
    @Cache(time = 1, timeUnit = TimeUnit.HOURS)
    public List<ForumTagSimple> selectListSimple() {
        return forumTagMapper.selectListSimple();
    }

    @Override
    @SetOperator(ForumTag.class)
    @CacheRefresh(method = "selectListSimple")
    public void insertForumTag(ForumTag forumTag) {
        forumTagMapper.insertForumTag(forumTag);
    }

    @Override
    public ForumTag selectById(Long tagId) {
        return forumTagMapper.selectById(tagId);
    }

    @Override
    public ForumTag selectByName(String tagName) {
        return forumTagMapper.selectByName(tagName);
    }
}
