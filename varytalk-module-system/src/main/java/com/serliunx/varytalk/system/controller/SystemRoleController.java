package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.framework.core.annotation.Logger;
import com.serliunx.varytalk.framework.core.entity.base.BaseController;
import com.serliunx.varytalk.framework.core.entity.result.Result;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
import com.serliunx.varytalk.system.entity.SystemRole;
import com.serliunx.varytalk.system.service.SystemRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class SystemRoleController extends BaseController {

    private final SystemRoleService systemRoleService;

    public SystemRoleController(SystemRoleService systemRoleService) {
        this.systemRoleService = systemRoleService;
    }

    @GetMapping("list")
    @ApiValidation(value = "system.role.list", group = PermissionGroup.class)
    public Result list(SystemRole systemRole){
        startPage();
        List<SystemRole> systemRoles = systemRoleService.selectList(systemRole);
        return page(systemRoles);
    }

    @PostMapping("add")
    @ApiValidation(value = "system.role.add", group = PermissionGroup.class)
    @Logger(opName = "角色接口", value = "添加一个新的角色")
    public Result add(@RequestBody @Validated SystemRole systemRole){
        if (systemRoleService.selectByName(systemRole.getRoleName()) != null) {
            return fail("该角色已存在, 请换个角色名试试!");
        }
        systemRoleService.insertRole(systemRole);
        return success(systemRole.getId());
    }

    @PutMapping("update")
    @ApiValidation(value = "system.role.update", group = PermissionGroup.class)
    @Logger(opName = "角色接口", value = "更新角色信息")
    public Result update(@RequestBody SystemRole role){
        return success();
    }

    @DeleteMapping("delete")
    @ApiValidation(value = "system.role.delete", group = PermissionGroup.class)
    @Logger(opName = "角色接口", value = "删除角色信息")
    public Result delete(Long roleId){
        return success();
    }
}
