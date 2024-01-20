package com.serliunx.varytalk.forum.controller;

import com.serliunx.varytalk.forum.entity.ForumTag;
import com.serliunx.varytalk.forum.service.ForumTagService;
import com.serliunx.varytalk.framework.core.annotation.Logger;
import com.serliunx.varytalk.framework.core.annotation.PermitAll;
import com.serliunx.varytalk.framework.core.entity.base.BaseController;
import com.serliunx.varytalk.framework.core.entity.result.CountResult;
import com.serliunx.varytalk.framework.core.entity.result.Result;
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
@RequestMapping("/forum-tag")
public class ForumTagController extends BaseController {

    private final ForumTagService forumTagService;

    public ForumTagController(ForumTagService forumTagService) {
        this.forumTagService = forumTagService;
    }

    @PostMapping("add")
    @Logger(opName = "论坛标签接口", value = "添加一个新的标签")
    @ApiValidation(value = "forum.tag.add", group = PermissionGroup.class)
    public Result add(@Validated @RequestBody ForumTag forumTag){
        ForumTag byName = forumTagService.selectByName(forumTag.getTagName());
        if(byName != null){
            return fail("该标签已存在, 请重试!");
        }
        forumTagService.insertForumTag(forumTag);
        return success(forumTag.getId());
    }

    @GetMapping("list")
    public Result list(ForumTag forumTag){
        startPage();
        List<ForumTag> forumTags = forumTagService.selectList(forumTag);
        return page(forumTags);
    }

    /**
     * 获取所有标签、忽略鉴权.
     */
    @GetMapping("list-all")
    @PermitAll
    public Result list(){
        return CountResult.success(forumTagService.selectListSimple());
    }
}
