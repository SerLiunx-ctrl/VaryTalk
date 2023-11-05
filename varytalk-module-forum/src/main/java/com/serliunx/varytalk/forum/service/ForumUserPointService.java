package com.serliunx.varytalk.forum.service;

import com.serliunx.varytalk.forum.entity.ForumPoint;
import com.serliunx.varytalk.forum.entity.ForumUserPoint;
import com.serliunx.varytalk.system.entity.SystemUser;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
public interface ForumUserPointService {

    void modify(ForumUserPoint forumUserPoint, ForumPoint forumPoint, SystemUser systemUser);

    void modify(ForumUserPoint forumUserPoint);

    List<ForumUserPoint> selectByUser(Long userId);
}
