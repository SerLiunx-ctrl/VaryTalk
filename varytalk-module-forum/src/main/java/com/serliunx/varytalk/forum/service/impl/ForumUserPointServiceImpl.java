package com.serliunx.varytalk.forum.service.impl;

import com.serliunx.varytalk.api.system.entity.User;
import com.serliunx.varytalk.api.system.user.SystemUserApi;
import com.serliunx.varytalk.common.exception.ServiceException;
import com.serliunx.varytalk.forum.entity.ForumPoint;
import com.serliunx.varytalk.forum.entity.ForumUserPoint;
import com.serliunx.varytalk.forum.mapper.ForumUserPointMapper;
import com.serliunx.varytalk.forum.service.ForumPointService;
import com.serliunx.varytalk.forum.service.ForumUserPointService;
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
    private final SystemUserApi systemUserApi;

    public ForumUserPointServiceImpl(ForumUserPointMapper forumUserPointMapper,
                                     ForumPointService forumPointService,
                                     SystemUserApi systemUserApi) {
        this.forumUserPointMapper = forumUserPointMapper;
        this.forumPointService = forumPointService;
        this.systemUserApi = systemUserApi;
    }

    @Override
    public void modify(ForumUserPoint forumUserPoint, ForumPoint forumPoint, User user) {
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
        User user = systemUserApi.getUserByIdFlatted(forumUserPoint.getUserId());
        if(user == null){
            throw new ServiceException("该用户不存在!", 400);
        }
        modify(forumUserPoint, forumPoint, user);
    }

    @Override
    public List<ForumUserPoint> selectByUser(Long userId) {
        return forumUserPointMapper.selectByUserId(userId);
    }
}
