package com.serliunx.varytalk.forum.mapper;

import com.serliunx.varytalk.forum.entity.ForumCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ForumCategoryMapper {

    List<ForumCategory> selectList(ForumCategory forumCategory);

    ForumCategory selectByName(String categoryName);

    Long insertForumCategory(ForumCategory forumCategory);
}
