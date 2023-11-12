package com.serliunx.varytalk.forum.mapper;

import com.serliunx.varytalk.forum.entity.ForumSection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Mapper
public interface ForumSectionMapper {

    List<ForumSection> selectList(ForumSection forumSection);

    void insertForumSection(ForumSection forumSection);

    ForumSection selectByName(String sectionName);

    List<ForumSection> selectByCategoryId(Long categoryId);
}
