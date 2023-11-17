package com.serliunx.varytalk.forum.mapper;

import com.serliunx.varytalk.forum.entity.ForumTag;
import com.serliunx.varytalk.forum.entity.simple.ForumTagSimple;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Mapper
public interface ForumTagMapper {

    List<ForumTag> selectList(ForumTag forumTag);

    List<ForumTagSimple> selectListSimple();

    void insertForumTag(ForumTag forumTag);

    ForumTag selectById(Long tagId);

    ForumTag selectByName(String tagName);
}
