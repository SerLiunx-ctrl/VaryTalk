package com.serliunx.varytalk.forum.mapper;

import com.serliunx.varytalk.forum.entity.ForumCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ForumCategoryMapper {

    List<ForumCategory> selectList(ForumCategory forumCategory);

    ForumCategory selectByName(String categoryName);

    ForumCategory selectById(Long id);

    Long insertForumCategory(ForumCategory forumCategory);
}
