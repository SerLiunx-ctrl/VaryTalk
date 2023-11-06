package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.annotation.RequiredPermission;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.system.entity.*;
import com.serliunx.varytalk.system.event.PermissionUpdateEvent;
import com.serliunx.varytalk.system.service.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("permission")
public class SystemPermissionController extends BaseController {

    private final SystemPermissionService systemPermissionService;
    private final SystemUserPermissionService systemUserPermissionService;
    private final SystemRolePermissionService systemRolePermissionService;
    private final SystemUserService systemUserService;
    private final SystemRoleService systemRoleService;
    private final ApplicationEventPublisher publisher;

    public SystemPermissionController(SystemPermissionService systemPermissionService,
                                      SystemUserService systemUserService,
                                      SystemRolePermissionService systemRolePermissionService,
                                      SystemRoleService systemRoleService,
                                      SystemUserPermissionService systemUserPermissionService,
                                      ApplicationEventPublisher publisher) {

        this.systemPermissionService = systemPermissionService;
        this.systemUserService = systemUserService;
        this.systemUserPermissionService = systemUserPermissionService;
        this.systemRolePermissionService = systemRolePermissionService;
        this.systemRoleService = systemRoleService;
        this.publisher = publisher;
    }

    /**
     * 添加一个权限节点
     * @param systemPermission 权限节点
     * @return 结果
     */
    @PostMapping("add")
    @RequiredPermission("system.permission.add")
    @Logger(opName = "权限接口", value = "添加一个新的权限节点")
    public Result add(@RequestBody @Validated SystemPermission systemPermission){
        if(systemPermissionService.selectByValue(systemPermission.getValue()) != null){
            return fail("该权限节点值已存在, 请重新输入!");
        }
        if(systemPermissionService.selectByName(systemPermission.getNodeName()) != null){
            return fail("该权限节点名称已存在, 换一个试试!");
        }
        systemPermissionService.insertPermission(systemPermission);
        return success(systemPermission.getId());
    }

    @PostMapping("give-user")
    @RequiredPermission("system.permission.give.user")
    @Logger(opName = "权限接口", value = "给用户添加权限")
    public Result give(@Validated SystemUserPermission systemUserPermission){
        SystemUser systemUser = systemUserService.selectUserById(systemUserPermission.getUserId());
        if(systemUser == null){
            return fail("指定用户不存在!");
        }
        SystemPermission systemPermission = systemPermissionService.selectById(systemUserPermission.getPermissionId());
        if(systemPermission == null){
            return fail("指定权限节点不存在!");
        }
        boolean result = systemUserPermissionService.checkIfGiven(systemUserPermission.getUserId(),
                systemUserPermission.getPermissionId());
        if(result){
            return fail("该用户已拥有该权限, 无法重复给予!");
        }
        systemUserPermissionService.insertUserPermission(systemUserPermission);
        return success(systemUserPermission.getId());
    }

    @PostMapping("give-role")
    @RequiredPermission("system.permission.give.role")
    @Logger(opName = "权限接口", value = "给角色添加权限")
    public Result giveRole(@Validated SystemRolePermission systemRolePermission){
        SystemRole systemRole = systemRoleService.selectById(systemRolePermission.getRoleId());
        if(systemRole == null){
            return fail("指定角色不存在!");
        }
        SystemPermission systemPermission = systemPermissionService.selectById(systemRolePermission.getPermissionId());
        if(systemPermission == null){
            return fail("指定权限节点不存在!");
        }
        boolean result = systemRolePermissionService.checkIfGiven(systemRolePermission.getRoleId(),
                systemRolePermission.getPermissionId());
        if(result){
            return fail("该角色已拥有该权限, 无法重复给予!");
        }
        systemRolePermissionService.insertRolePermission(systemRolePermission);
        return success(systemRolePermission.getId());
    }

    @GetMapping("user-permissions")
    @RequiredPermission("system.permission.get.users")
    public Result getUserPermissions(Long userId){
        if(userId == null){
            return fail("请指定用户id!");
        }
        SystemUser systemUser = systemUserService.selectUserById(userId);
        if(systemUser == null){
            return fail("指定用户不存在!");
        }
        startPage();
        List<SystemUserPermission> systemUserPermissions = systemUserPermissionService.selectByUserId(userId);
        return page(systemUserPermissions);
    }

    @GetMapping("role-permissions")
    @RequiredPermission("system.permission.get.roles")
    public Result getRolePermissions(Long roleId){
        if(roleId == null){
            return fail("请指定角色id!");
        }
        SystemRole systemRole = systemRoleService.selectById(roleId);
        if(systemRole == null){
            return fail("指定角色不存在!");
        }
        startPage();
        List<SystemRolePermission> systemRolePermissions = systemRolePermissionService.selectByRoleId(roleId);
        return page(systemRolePermissions);
    }

    /**
     * 获取权限节点列表
     * @param systemPermission 查询参数
     * @return 节点列表
     */
    @GetMapping("list")
    @RequiredPermission("system.permission.list")
    public Result list(SystemPermission systemPermission){
        startPage();
        List<SystemPermission> systemPermissions = systemPermissionService.selectList(systemPermission);
        return page(systemPermissions);
    }
}
