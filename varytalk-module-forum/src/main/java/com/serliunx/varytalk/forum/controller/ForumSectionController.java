package com.serliunx.varytalk.forum.controller;

import com.serliunx.varytalk.forum.entity.ForumCategory;
import com.serliunx.varytalk.forum.entity.ForumSection;
import com.serliunx.varytalk.forum.service.ForumCategoryService;
import com.serliunx.varytalk.forum.service.ForumSectionService;
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
 * 论坛板块控制
 * @author SerLiunx
 * @since 1.0
 */
@RestController
@RequestMapping("forum-section")
public class ForumSectionController extends BaseController {

    private final ForumSectionService forumSectionService;
    private final ForumCategoryService forumCategoryService;

    public ForumSectionController(ForumSectionService forumSectionService,
                                  ForumCategoryService forumCategoryService) {
        this.forumSectionService = forumSectionService;
        this.forumCategoryService = forumCategoryService;
    }

    @PostMapping("add")
    @ApiValidation(value = "forum.section.add", group = PermissionGroup.class)
    @Logger(opName = "论坛板块接口", value = "添加一个新的板块")
    public Result add(@RequestBody @Validated ForumSection forumSection){
        ForumCategory forumCategory = forumCategoryService.selectById(forumSection.getCategoryId());
        if(forumCategory == null){
            return fail("指定分区不存在!");
        }
        ForumSection section = forumSectionService.selectByName(forumSection.getSectionName());
        if(section != null){
            return fail("已存在同名板块!");
        }
        forumSectionService.insertForumSection(forumSection);
        return success(forumSection.getId());
    }

    @GetMapping("list")
    @ApiValidation(value = "forum.section.list", group = PermissionGroup.class)
    public Result list(ForumSection forumSection){
        startPage();
        List<ForumSection> forumSections = forumSectionService.selectList(forumSection);
        return page(forumSections);
    }

    @PermitAll
    @GetMapping("get-by-category/{id}")
    public Result getByCategory(@PathVariable("id") Long id){
        return CountResult.success(forumSectionService.selectByCategoryId(id));
    }
}
