package com.serliunx.varytalk.forum.controller;

import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.forum.entity.ForumPoint;
import com.serliunx.varytalk.forum.entity.ForumUserPoint;
import com.serliunx.varytalk.forum.service.ForumPointService;
import com.serliunx.varytalk.forum.service.ForumUserPointService;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.SystemUserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户积分数量控制层
 * @author SerLiunx
 * @since 1.0
 */
@RestController
@RequestMapping("forum-user-points")
public class ForumUserPointController extends BaseController {

    private final ForumUserPointService forumUserPointService;
    private final ForumPointService forumPointService;
    private final SystemUserService systemUserService;

    public ForumUserPointController(ForumUserPointService forumUserPointService,
                                    ForumPointService forumPointService,
                                    SystemUserService systemUserService) {
        this.forumUserPointService = forumUserPointService;
        this.forumPointService = forumPointService;
        this.systemUserService = systemUserService;
    }

    @PutMapping("modify")
    public Result modify(@Validated ForumUserPoint forumUserPoint){
        ForumPoint forumPoint = forumPointService.selectById(forumUserPoint.getPointId());
        if(forumPoint == null){
            return fail("不存在该积分项!");
        }
        SystemUser systemUser = systemUserService.selectUserById(forumUserPoint.getUserId());
        if(systemUser == null){
            return fail("该用户不存在!");
        }
        forumUserPointService.modify(forumUserPoint, forumPoint, systemUser);
        return success();
    }

    @GetMapping("user-owned")
    public Result modify(Long userId){
        SystemUser systemUser = systemUserService.selectUserById(userId);
        if(systemUser == null){
            return fail("该用户不存在!");
        }
        List<ForumUserPoint> forumUserPoints = forumUserPointService.selectByUser(userId);
        return success(forumUserPoints.isEmpty() ? null : forumUserPoints);
    }
}
