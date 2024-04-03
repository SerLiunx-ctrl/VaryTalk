package com.serliunx.varytalk.forum.controller;

import com.serliunx.varytalk.forum.entity.ForumCategory;
import com.serliunx.varytalk.forum.entity.ForumSection;
import com.serliunx.varytalk.forum.entity.simple.ForumCategorySimple;
import com.serliunx.varytalk.forum.service.ForumCategoryService;
import com.serliunx.varytalk.forum.service.ForumSectionService;
import com.serliunx.varytalk.framework.core.annotation.Logger;
import com.serliunx.varytalk.framework.core.annotation.PermitAll;
import com.serliunx.varytalk.framework.core.entity.base.BaseController;
import com.serliunx.varytalk.framework.core.entity.result.Result;
import com.serliunx.varytalk.framework.core.tool.BeanUtils;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 论坛分区控制器
 * @author SerLiunx
 * @since 1.0
 */
@RestController
@RequestMapping("forum-category")
public class ForumCategoryController extends BaseController {

    @Resource
    private ForumCategoryService forumCategoryService;
    @Resource
    private ForumSectionService forumSectionService;

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

    @PermitAll
    @GetMapping("list-simple")
    public Result listSimple(){
        List<ForumCategorySimple> result = BeanUtils.toBean(forumCategoryService.selectSimpleList(),
                ForumCategorySimple.class);
        List<Long> categoryIds = result.stream()
                .map(ForumCategorySimple::getId)
                .toList();
        Map<Long, List<ForumSection>> forumSectionMap = forumSectionService.selectByCategoryIds(categoryIds).stream()
                .collect(Collectors.groupingBy(ForumSection::getCategoryId));
        result.forEach(r -> r.setForumSections(forumSectionMap.get(r.getId())));
        return success(result);
    }
}
