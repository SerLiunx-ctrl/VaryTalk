package github.serliunx.varytalk.project.system.controller;

import github.serliunx.varytalk.common.annotation.PermissionRequired;
import github.serliunx.varytalk.common.base.BaseController;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.project.system.entity.SystemRole;
import github.serliunx.varytalk.project.system.service.SystemRoleService;
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
    @PermissionRequired("system.role.list")
    public Result list(SystemRole systemRole){
        startPage();
        List<SystemRole> systemRoles = systemRoleService.selectList(systemRole);
        return page(systemRoles);
    }

    @PostMapping("add")
    @PermissionRequired("system.role.add")
    public Result add(@RequestBody @Validated SystemRole systemRole){
        if (systemRoleService.selectByName(systemRole.getRoleName()) != null) {
            return fail("该角色已存在, 请换个角色名试试!");
        }
        systemRoleService.insertRole(systemRole);
        return success(systemRole.getId());
    }
}
