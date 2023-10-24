package com.serliunx.varytalk.forum.service;

import com.serliunx.varytalk.forum.entity.ForumPoint;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
public interface ForumPointService {

    List<ForumPoint> selectList(ForumPoint forumPoint);

    Long insertForumPoint(ForumPoint forumPoint);

    boolean checkByPointTag(String pointTag);
}
