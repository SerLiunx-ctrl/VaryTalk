package com.serliunx.varytalk.forum.controller;

import com.serliunx.varytalk.api.system.SystemUserApi;
import com.serliunx.varytalk.api.system.entity.User;
import com.serliunx.varytalk.framework.core.entity.base.BaseController;
import com.serliunx.varytalk.framework.core.entity.result.Result;
import com.serliunx.varytalk.forum.entity.ForumPoint;
import com.serliunx.varytalk.forum.entity.ForumUserPoint;
import com.serliunx.varytalk.forum.service.ForumPointService;
import com.serliunx.varytalk.forum.service.ForumUserPointService;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
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
    private final SystemUserApi systemUserApi;

    public ForumUserPointController(ForumUserPointService forumUserPointService,
                                    ForumPointService forumPointService,
                                    SystemUserApi systemUserApi) {
        this.forumUserPointService = forumUserPointService;
        this.forumPointService = forumPointService;
        this.systemUserApi = systemUserApi;
    }

    @PutMapping("modify")
    @ApiValidation(value = "forum.point.user.modify", group = PermissionGroup.class)
    public Result modify(@Validated ForumUserPoint forumUserPoint){
        ForumPoint forumPoint = forumPointService.selectById(forumUserPoint.getPointId());
        if(forumPoint == null){
            return fail("不存在该积分项!");
        }
        User user = systemUserApi.getUserByIdFlatted(forumUserPoint.getUserId());
        if(user == null){
            return fail("该用户不存在!");
        }
        forumUserPointService.modify(forumUserPoint, forumPoint, user);
        return success();
    }

    @GetMapping("user-owned")
    public Result userOwned(Long userId){
        User user = systemUserApi.getUserByIdFlatted(userId);
        if(user == null){
            return fail("该用户不存在!");
        }
        List<ForumUserPoint> forumUserPoints = forumUserPointService.selectByUser(userId);
        return success(forumUserPoints.isEmpty() ? null : forumUserPoints);
    }
}
