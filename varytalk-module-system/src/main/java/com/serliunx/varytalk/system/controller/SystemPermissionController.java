package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.framework.core.annotation.Logger;
import com.serliunx.varytalk.framework.core.entity.base.BaseController;
import com.serliunx.varytalk.framework.core.entity.result.Result;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
import com.serliunx.varytalk.system.entity.*;
import com.serliunx.varytalk.system.service.*;
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

    public SystemPermissionController(SystemPermissionService systemPermissionService,
                                      SystemUserService systemUserService,
                                      SystemRolePermissionService systemRolePermissionService,
                                      SystemRoleService systemRoleService,
                                      SystemUserPermissionService systemUserPermissionService
    ) {

        this.systemPermissionService = systemPermissionService;
        this.systemUserService = systemUserService;
        this.systemUserPermissionService = systemUserPermissionService;
        this.systemRolePermissionService = systemRolePermissionService;
        this.systemRoleService = systemRoleService;
    }

    /**
     * 添加一个权限节点
     * @param systemPermission 权限节点
     * @return 结果
     */
    @PostMapping("add")
    @ApiValidation(value = "system.permission.add", group = PermissionGroup.class)
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
    @ApiValidation(value = "system.permission.give.user", group = PermissionGroup.class)
    @Logger(opName = "权限接口", value = "给用户添加权限")
    public Result give(@Validated SystemUserPermission systemUserPermission){
        SystemUser systemUser = systemUserService.selectUserByIdFlatted(systemUserPermission.getUserId());
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
    @ApiValidation(value = "system.permission.give.role", group = PermissionGroup.class)
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
    @ApiValidation(value = "system.permission.get.users", group = PermissionGroup.class)
    public Result getUserPermissions(SystemUserPermission systemUserPermission){
        Long userId = systemUserPermission.getUserId();

        if(userId == null){
            return fail("请指定用户id!");
        }
        SystemUser systemUser = systemUserService.selectUserById(userId);
        if(systemUser == null){
            return fail("指定用户不存在!");
        }
        startPage();
        List<SystemUserPermission> systemUserPermissions = systemUserPermissionService.selectList(systemUserPermission);
        return page(systemUserPermissions);
    }

    @GetMapping("role-permissions")
    @ApiValidation(value = "system.permission.get.roles", group = PermissionGroup.class)
    public Result getRolePermissions(SystemRolePermission systemRolePermission){
        Long roleId = systemRolePermission.getRoleId();
        if(roleId == null){
            return fail("请指定角色id!");
        }
        SystemRole systemRole = systemRoleService.selectById(roleId);
        if(systemRole == null){
            return fail("指定角色不存在!");
        }
        startPage();
        List<SystemRolePermission> systemRolePermissions = systemRolePermissionService.selectList(systemRolePermission);
        return page(systemRolePermissions);
    }

    /**
     * 获取权限节点列表
     * @param systemPermission 查询参数
     * @return 节点列表
     */
    @GetMapping("list")
    @ApiValidation(value = "system.permission.list", group = PermissionGroup.class)
    public Result list(SystemPermission systemPermission){
        startPage();
        List<SystemPermission> systemPermissions = systemPermissionService.selectList(systemPermission);
        return page(systemPermissions);
    }
}
