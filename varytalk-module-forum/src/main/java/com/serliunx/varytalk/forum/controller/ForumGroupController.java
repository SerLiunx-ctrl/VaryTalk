package com.serliunx.varytalk.forum.controller;

import com.serliunx.varytalk.api.system.entity.User;
import com.serliunx.varytalk.api.system.user.SystemUserApi;
import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.common.validation.forum.ForumGroupAddGroup;
import com.serliunx.varytalk.forum.entity.ForumGroup;
import com.serliunx.varytalk.forum.service.ForumGroupService;
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
@RequestMapping("forum-group")
public class ForumGroupController extends BaseController {

    private final ForumGroupService forumGroupService;
    private final SystemUserApi systemUserApi;

    public ForumGroupController(ForumGroupService forumGroupService, SystemUserApi systemUserApi) {
        this.forumGroupService = forumGroupService;
        this.systemUserApi = systemUserApi;
    }

    /**
     * 新增群组(此接口为管理员或有权限的用户直接添加)
     * <p>请与创建群组的接口区分开
     */
    @PostMapping("add")
    @ApiValidation(value = "forum.group.add", group = PermissionGroup.class)
    @Logger(opName = "论坛群组接口", value = "添加一个新的群组")
    public Result add(@RequestBody @Validated(ForumGroupAddGroup.class) ForumGroup forumGroup){
        if(forumGroup.getOwnerId() == null){
            forumGroup.setOwnerId(SecurityUtils.getUserId());
        }else{
            User user = systemUserApi.getUserByIdFlatted(forumGroup.getOwnerId());
            if(user == null){
                return fail("不存在指定用户, 请换一个用户试试!");
            }
        }
        ForumGroup byOwnerId = forumGroupService.selectByOwnerId(forumGroup.getOwnerId());
        if(byOwnerId != null){
            return fail("一个用户只允许创建一个群组!");
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
    @ApiValidation(value = "forum.group.edit", group = PermissionGroup.class)
    public Result edit(@RequestBody ForumGroup forumGroup){
        return success();
    }

    @GetMapping("list")
    @ApiValidation(value = "forum.group.list", group = PermissionGroup.class)
    public Result list(ForumGroup forumGroup){
        startPage();
        List<ForumGroup> forumGroups = forumGroupService.selectList(forumGroup);
        return page(forumGroups);
    }
}
