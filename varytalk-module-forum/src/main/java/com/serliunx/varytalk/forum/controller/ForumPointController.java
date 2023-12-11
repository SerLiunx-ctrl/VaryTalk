package com.serliunx.varytalk.forum.controller;

import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.common.validation.forum.ForumPointInsertGroup;
import com.serliunx.varytalk.forum.entity.ForumPoint;
import com.serliunx.varytalk.forum.service.ForumPointService;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@RestController
@RequestMapping("forum-point")
public class ForumPointController extends BaseController {

    private final ForumPointService forumPointService;

    public ForumPointController(ForumPointService forumPointService) {
        this.forumPointService = forumPointService;
    }

    @GetMapping("list")
    @ApiValidation(value = "forum.point.type.list", group = PermissionGroup.class)
    public Result list(ForumPoint forumPoint){
        startPage();
        List<ForumPoint> forumPoints = forumPointService.selectList(forumPoint);
        return page(forumPoints);
    }

    @PostMapping("add")
    @ApiValidation(value = "forum.point.type.add", group = PermissionGroup.class)
    @Logger(opName = "论坛积分接口", value = "添加一个新的积分类型")
    public Result add(@RequestBody @Validated(ForumPointInsertGroup.class) ForumPoint forumPoint){
        if(forumPointService.checkByPointTag(forumPoint.getPointTag())){
            return fail("该积分已存在, 换一个标签试试!");
        }
        forumPointService.insertForumPoint(forumPoint);
        return success(forumPoint.getId());
    }
}
