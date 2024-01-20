package com.serliunx.varytalk.forum.service.impl;

import com.serliunx.varytalk.framework.core.annotation.SetOperator;
import com.serliunx.varytalk.forum.entity.ForumPoint;
import com.serliunx.varytalk.forum.mapper.ForumPointMapper;
import com.serliunx.varytalk.forum.service.ForumPointService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class ForumPointServiceImpl implements ForumPointService {

    private final ForumPointMapper forumPointMapper;

    public ForumPointServiceImpl(ForumPointMapper forumPointMapper) {
        this.forumPointMapper = forumPointMapper;
    }

    @Override
    public List<ForumPoint> selectList(ForumPoint forumPoint) {
        return forumPointMapper.selectList(forumPoint);
    }

    @Override
    @SetOperator(ForumPoint.class)
    public Long insertForumPoint(ForumPoint forumPoint) {
        return forumPointMapper.insertForumPoint(forumPoint);
    }

    @Override
    public boolean checkByPointTag(String pointTag) {
        return forumPointMapper.checkByPointTag(pointTag) != null;
    }

    @Override
    public ForumPoint selectById(Long id) {
        return forumPointMapper.selectById(id);
    }
}
