package com.serliunx.varytalk.forum.controller;

import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.annotation.RequiredPermission;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.common.validation.forum.ForumGroupAddGroup;
import com.serliunx.varytalk.forum.entity.ForumGroup;
import com.serliunx.varytalk.forum.service.ForumGroupService;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.SystemUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SerLiunx
 * @since 1.0
 */
@RestController
@RequestMapping("forum-group")
public class ForumGroupController extends BaseController {

    private final ForumGroupService forumGroupService;
    private final SystemUserService systemUserService;

    public ForumGroupController(ForumGroupService forumGroupService,
                                SystemUserService systemUserService) {
        this.forumGroupService = forumGroupService;
        this.systemUserService = systemUserService;
    }

    /**
     * 新增群组(此接口为管理员或有权限的用户直接添加)
     * <p>请与创建群组的接口区分开
     */
    @PostMapping("add")
    @RequiredPermission("forum.group.add")
    @Logger(opName = "论坛群组接口", value = "添加一个新的群组")
    public Result add(@RequestBody @Validated(ForumGroupAddGroup.class) ForumGroup forumGroup){
        if(forumGroup.getOwnerId() == null){
            forumGroup.setOwnerId(SecurityUtils.getUserId());
        }else{
            SystemUser systemUser = systemUserService.selectUserByIdFlatted(forumGroup.getOwnerId());
            if(systemUser == null){
                return fail("不存在指定用户, 请换一个用户试试!");
            }
        }
        ForumGroup byTag = forumGroupService.selectByTag(forumGroup.getGroupTag());
        if(byTag != null){
            return fail("该群组标签已存在, 请换一个试试!");
        }
        ForumGroup byName = forumGroupService.selectByName(forumGroup.getGroupName());
        if(byName != null){
            return fail("该群组名称已存在, 请换一个试试!");
        }
        forumGroupService.insertForumGroup(forumGroup);
        return success(forumGroup.getId());
    }

    /**
     * 编辑群组信息(管理端), 请与更新群组信息的接口区分开.
     */
    @PutMapping("edit")
    @RequiredPermission("forum.group.edit")
    public Result edit(@RequestBody ForumGroup forumGroup){
        return success();
    }

    @GetMapping("list")
    @RequiredPermission("forum.group.list")
    public Result list(ForumGroup forumGroup){
        startPage();
        List<ForumGroup> forumGroups = forumGroupService.selectList(forumGroup);
        return page(forumGroups);
    }
}
