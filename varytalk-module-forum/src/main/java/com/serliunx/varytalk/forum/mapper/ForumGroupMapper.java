package com.serliunx.varytalk.forum.mapper;

import com.serliunx.varytalk.forum.entity.ForumGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Mapper
public interface ForumGroupMapper {

    List<ForumGroup> selectList(ForumGroup forumGroup);

    ForumGroup selectById(Long groupId);

    ForumGroup selectByName(String groupName);

    ForumGroup selectByTag(String groupTag);

    void insertForumGroup(ForumGroup forumGroup);
}
