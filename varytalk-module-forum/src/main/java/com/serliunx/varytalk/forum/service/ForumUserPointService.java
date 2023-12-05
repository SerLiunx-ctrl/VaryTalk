package com.serliunx.varytalk.forum.service;

import com.serliunx.varytalk.api.system.entity.User;
import com.serliunx.varytalk.forum.entity.ForumPoint;
import com.serliunx.varytalk.forum.entity.ForumUserPoint;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
public interface ForumUserPointService {

    void modify(ForumUserPoint forumUserPoint, ForumPoint forumPoint, User user);

    void modify(ForumUserPoint forumUserPoint);

    List<ForumUserPoint> selectByUser(Long userId);
}
