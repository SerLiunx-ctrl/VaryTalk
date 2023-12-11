package com.serliunx.varytalk.forum.controller;

import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.forum.entity.ForumCategory;
import com.serliunx.varytalk.forum.service.ForumCategoryService;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 论坛分区控制器
 * @author SerLiunx
 * @since 1.0
 */
@RestController
@RequestMapping("forum-category")
public class ForumCategoryController extends BaseController {

    private final ForumCategoryService forumCategoryService;

    public ForumCategoryController(ForumCategoryService forumCategoryService) {
        this.forumCategoryService = forumCategoryService;
    }

    @GetMapping("list")
    @ApiValidation(value = "forum.category.list.detail", group = PermissionGroup.class)
    public Result list(ForumCategory forumCategory){
        startPage();
        List<ForumCategory> forumCategories = forumCategoryService.selectList(forumCategory);
        return page(forumCategories);
    }

    @PostMapping("add")
    @ApiValidation(value = "forum.category.add", group = PermissionGroup.class)
    @Logger(opName = "论坛分区接口", value = "添加一个新的分区")
    public Result add(@RequestBody ForumCategory forumCategory){
        ForumCategory fc = forumCategoryService.selectByName(forumCategory.getCategoryName());
        if(fc != null){
            return fail("该分区名称已存在, 换一个试试!");
        }
        forumCategoryService.insertForumCategory(forumCategory);
        return success(forumCategory.getId());
    }
}
