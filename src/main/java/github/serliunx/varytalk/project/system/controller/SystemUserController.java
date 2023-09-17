package github.serliunx.varytalk.project.system.controller;

import github.serliunx.varytalk.common.annotation.PermissionRequired;
import github.serliunx.varytalk.common.base.BaseController;
import github.serliunx.varytalk.common.result.CountResult;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.common.validation.group.SystemUserRoleUpdateGroup;
import github.serliunx.varytalk.project.system.entity.SystemRole;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.service.SystemRoleService;
import github.serliunx.varytalk.project.system.service.SystemUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class SystemUserController extends BaseController {

    private final SystemUserService systemUserService;
    private final SystemRoleService systemRoleService;

    public SystemUserController(SystemUserService systemUserService, SystemRoleService systemRoleService) {
        this.systemUserService = systemUserService;
        this.systemRoleService = systemRoleService;
    }

    @GetMapping("list")
    @PermissionRequired("system.user.list")
    public Result list(SystemUser systemUser){
        startPage();
        List<SystemUser> systemUsers = systemUserService.selectList(systemUser);
        return page(systemUsers);
    }

    @GetMapping("get/{id}")
    @PermissionRequired("system.user.get")
    public Result getUserById(@PathVariable Long id){
        if(id == null || id < 0){
           return fail("id有误, 请重试.");
        }
        return success(systemUserService.selectUserById(id));
    }

    @PostMapping("add")
    @PermissionRequired("system.user.add")
    public Result add(@RequestBody @Validated SystemUser systemUser){
        String result = systemUserService.checkUserInformation(systemUser);
        if(result != null){
            return fail(result);
        }
        systemUserService.insertUser(systemUser);
        return Result.success(systemUser.getId());
    }

    @PutMapping("bind-role")
    @PermissionRequired("system.user.bind.role")
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
    @PermissionRequired("system.user.online.detail")
    public Result getOnline(){
        return CountResult.success(systemUserService.getOnlineUser());
    }
}
