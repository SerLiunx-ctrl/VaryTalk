package com.serliunx.varytalk.forum.mapper;

import com.serliunx.varytalk.forum.entity.ForumPoint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Mapper
public interface ForumPointMapper {

    List<ForumPoint> selectList(ForumPoint forumPoint);

    Long insertForumPoint(ForumPoint forumPoint);

    ForumPoint checkByPointTag(String pointTag);

    ForumPoint selectById(Long id);
}
