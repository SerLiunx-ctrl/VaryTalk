package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.annotation.RequiredPermission;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.result.Result;
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
    @RequiredPermission("system.role.list")
    public Result list(SystemRole systemRole){
        startPage();
        List<SystemRole> systemRoles = systemRoleService.selectList(systemRole);
        return page(systemRoles);
    }

    @PostMapping("add")
    @RequiredPermission("system.role.add")
    @Logger(opName = "角色接口", value = "添加一个新的角色")
    public Result add(@RequestBody @Validated SystemRole systemRole){
        if (systemRoleService.selectByName(systemRole.getRoleName()) != null) {
            return fail("该角色已存在, 请换个角色名试试!");
        }
        systemRoleService.insertRole(systemRole);
        return success(systemRole.getId());
    }
}
