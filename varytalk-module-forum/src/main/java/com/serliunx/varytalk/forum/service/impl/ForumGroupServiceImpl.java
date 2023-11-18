package com.serliunx.varytalk.forum.service.impl;

import com.serliunx.varytalk.common.annotation.SetOperator;
import com.serliunx.varytalk.forum.entity.ForumGroup;
import com.serliunx.varytalk.forum.mapper.ForumGroupMapper;
import com.serliunx.varytalk.forum.service.ForumGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class ForumGroupServiceImpl implements ForumGroupService {

    private final ForumGroupMapper forumGroupMapper;

    public ForumGroupServiceImpl(ForumGroupMapper forumGroupMapper) {
        this.forumGroupMapper = forumGroupMapper;
    }

    @Override
    public List<ForumGroup> selectList(ForumGroup forumGroup) {
        return forumGroupMapper.selectList(forumGroup);
    }

    @Override
    public ForumGroup selectById(Long groupId) {
        return forumGroupMapper.selectById(groupId);
    }

    @Override
    public ForumGroup selectByName(String groupName) {
        return forumGroupMapper.selectByName(groupName);
    }

    @Override
    public ForumGroup selectByTag(String groupTag) {
        return forumGroupMapper.selectByTag(groupTag);
    }

    @Override
    @SetOperator(ForumGroup.class)
    public void insertForumGroup(ForumGroup forumGroup) {
        forumGroupMapper.insertForumGroup(forumGroup);
    }
}
