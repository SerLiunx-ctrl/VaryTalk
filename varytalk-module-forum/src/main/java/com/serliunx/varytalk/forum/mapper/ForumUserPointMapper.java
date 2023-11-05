package com.serliunx.varytalk.forum.mapper;

import com.serliunx.varytalk.forum.entity.ForumUserPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Mapper
public interface ForumUserPointMapper {

    ForumUserPoint select(@Param("pointId") Long pointId, @Param("userId") Long userId);

    void insert(ForumUserPoint forumUserPoint);

    void update(ForumUserPoint forumUserPoint);

    List<ForumUserPoint> selectByUserId(Long userId);
}
