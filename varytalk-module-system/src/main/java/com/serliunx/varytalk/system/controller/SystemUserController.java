package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.result.CountResult;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.common.util.SecurityUtils;
import com.serliunx.varytalk.common.validation.system.SystemUserRoleUpdateGroup;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
import com.serliunx.varytalk.system.entity.SystemRole;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.service.SystemRoleService;
import com.serliunx.varytalk.system.service.SystemUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class SystemUserController extends BaseController {

    private final SystemUserService systemUserService;
    private final SystemRoleService systemRoleService;

    public SystemUserController(SystemUserService systemUserService,
                                SystemRoleService systemRoleService) {
        this.systemUserService = systemUserService;
        this.systemRoleService = systemRoleService;
    }

    @GetMapping("list")
    @ApiValidation(value = "system.user.list", group = PermissionGroup.class)
    public Result list(SystemUser systemUser){
        startPage();
        List<SystemUser> systemUsers = systemUserService.selectList(systemUser);
        return page(systemUsers);
    }

    @GetMapping("get/{id}")
    @ApiValidation(value = "system.user.get", group = PermissionGroup.class)
    public Result getUserById(@PathVariable Long id){
        if(id == null || id < 0){
           return fail("id有误, 请重试.");
        }
        return success(systemUserService.selectUserById(id));
    }

    @PostMapping("add")
    @ApiValidation(value = "system.user.add", group = PermissionGroup.class)
    @Logger(opName = "用户接口", value = "添加一个用户")
    public Result add(@RequestBody @Validated SystemUser systemUser){
        String result = systemUserService.checkUserInformation(systemUser);
        if(result != null){
            return fail(result);
        }
        systemUserService.insertUser(systemUser);
        return Result.success(systemUser.getId());
    }

    @PutMapping("update-basic-info")
    public Result update(@RequestBody SystemUser systemUser){
        if(systemUser.getId() == null){
            Long userId = SecurityUtils.getUserId();
            systemUser.setId(userId);
        }
        if(systemUser.getEmail() != null && systemUserService.checkUserByEmail(systemUser.getEmail(),
                true)){
            return fail("该邮箱已被使用, 请换一个试试!");
        }
        if(systemUser.getPhoneNumber() != null && systemUserService.checkUserByPhoneNumber(systemUser.getPhoneNumber(),
                true)){
            return fail("该手机号已被使用, 请换一个试试!");
        }

        systemUserService.updateUser(systemUser);
        return success();
    }

    @PutMapping("bind-role")
    @ApiValidation(value = "system.user.bind.role", group = PermissionGroup.class)
    @Logger(opName = "用户接口", value = "修改用户的角色")
    public Result bindRole(@Validated(SystemUserRoleUpdateGroup.class) SystemUser systemUser){
        SystemUser systemUserFound = systemUserService.selectUserById(systemUser.getId());
        if(systemUserFound == null){
            return fail("未找到该用户!");
        }
        SystemRole systemRole = systemRoleService.selectById(systemUser.getRoleId());
        if(systemRole == null){
            return fail("未找到该角色!");
        }
        systemUserService.updateRole(systemUser);
        return success();
    }

    @GetMapping("online")
    @ApiValidation(value = "system.user.online.detail", group = PermissionGroup.class)
    public Result getOnline(){
        return CountResult.success(systemUserService.getOnlineUser());
    }
}
