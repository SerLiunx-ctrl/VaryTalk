package github.serliunx.varytalk.project.system.controller;

import github.serliunx.varytalk.common.base.BaseController;
import github.serliunx.varytalk.common.result.CountResult;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.service.SystemUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class SystemUserController extends BaseController {

    private final SystemUserService systemUserService;

    public SystemUserController(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @GetMapping("list")
    public Result list(@RequestBody(required = false) SystemUser systemUser){
        startPage();
        List<SystemUser> systemUsers = systemUserService.selectList(systemUser);
        return page(systemUsers);
    }

    @GetMapping("get/{id}")
    public Result getUserById(@PathVariable Long id){
        if(id == null || id < 0){
           return fail("id有误, 请重试.");
        }
        return success(systemUserService.selectUserById(id));
    }

    @PostMapping("add")
    public Result add(@RequestBody @Validated SystemUser systemUser){
        String result = systemUserService.checkUserInformation(systemUser);
        if(result != null){
            return fail(result);
        }
        systemUserService.insertUser(systemUser);
        return Result.success(systemUser.getId());
    }

    @GetMapping("online")
    public Result getOnline(){
        return CountResult.success(systemUserService.getOnlineUser());
    }
}
