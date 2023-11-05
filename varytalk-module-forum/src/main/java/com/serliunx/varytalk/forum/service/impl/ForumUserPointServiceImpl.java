package com.serliunx.varytalk.forum.service.impl;

import com.serliunx.varytalk.common.exception.ServiceException;
import com.serliunx.varytalk.forum.entity.ForumPoint;
import com.serliunx.varytalk.forum.entity.ForumUserPoint;
import com.serliunx.varytalk.forum.mapper.ForumUserPointMapper;
import com.serliunx.varytalk.forum.service.ForumPointService;
import com.serliunx.varytalk.forum.service.ForumUserPointService;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.SystemUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class ForumUserPointServiceImpl implements ForumUserPointService {

    private final ForumUserPointMapper forumUserPointMapper;
    private final ForumPointService forumPointService;
    private final SystemUserService systemUserService;

    public ForumUserPointServiceImpl(ForumUserPointMapper forumUserPointMapper,
                                     ForumPointService forumPointService,
                                     SystemUserService systemUserService) {
        this.forumUserPointMapper = forumUserPointMapper;
        this.forumPointService = forumPointService;
        this.systemUserService = systemUserService;
    }

    @Override
    public void modify(ForumUserPoint forumUserPoint, ForumPoint forumPoint, SystemUser systemUser) {
        ForumUserPoint entity = forumUserPointMapper.select(forumUserPoint.getPointId(), forumUserPoint.getUserId());
        //不存在则新建
        if(entity == null){
            forumUserPoint.setRemark(String.format("该用户 %s 的数量", forumPoint.getPointName()));
            forumUserPointMapper.insert(forumUserPoint);
            return;
        }
        Long pointsOwned = entity.getPoints();
        Long value = forumUserPoint.getPoints();
        long newValue = pointsOwned + value;
        if(newValue < 0){
            throw new ServiceException(String.format("%s余额不足!", forumPoint.getPointName()), 400);
        }
        forumUserPoint.setPoints(newValue);
        forumUserPointMapper.update(forumUserPoint);
    }

    @Override
    public void modify(ForumUserPoint forumUserPoint) {
        ForumPoint forumPoint = forumPointService.selectById(forumUserPoint.getPointId());
        if(forumPoint == null){
            throw new ServiceException("不存在该积分项!", 400);
        }
        SystemUser systemUser = systemUserService.selectUserById(forumUserPoint.getUserId());
        if(systemUser == null){
            throw new ServiceException("该用户不存在!", 400);
        }
        modify(forumUserPoint, forumPoint, systemUser);
    }

    @Override
    public List<ForumUserPoint> selectByUser(Long userId) {
        return forumUserPointMapper.selectByUserId(userId);
    }
}
