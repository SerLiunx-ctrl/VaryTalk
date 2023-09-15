package github.serliunx.varytalk.project.system.controller;

import github.serliunx.varytalk.common.base.BaseController;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.project.system.entity.SystemPermission;
import github.serliunx.varytalk.project.system.service.SystemPermissionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("permission")
public class SystemPermissionController extends BaseController {

    private final SystemPermissionService systemPermissionService;

    public SystemPermissionController(SystemPermissionService systemPermissionService) {
        this.systemPermissionService = systemPermissionService;
    }

    /**
     * 添加一个权限节点
     * @param systemPermission 权限节点
     * @return 结果
     */
    @PostMapping("add")
    public Result add(@RequestBody @Validated SystemPermission systemPermission){
        if(systemPermissionService.selectByValue(systemPermission.getValue()) != null){
            return fail("该权限节点值已存在, 请重新输入!");
        }
        if(systemPermissionService.selectByName(systemPermission.getNodeName()) != null){
            return fail("该权限节点名称已存在, 换一个试试!");
        }
        systemPermissionService.insertPermission(systemPermission);
        return success();
    }

    /**
     * 获取权限节点列表
     * @param systemPermission 查询参数
     * @return 节点列表
     */
    @GetMapping("list")
    public Result list(@RequestBody(required = false) SystemPermission systemPermission){
        startPage();
        List<SystemPermission> systemPermissions = systemPermissionService.selectList(systemPermission);
        return page(systemPermissions);
    }
}
