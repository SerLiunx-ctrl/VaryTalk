package github.serliunx.varytalk.project.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import github.serliunx.varytalk.common.base.BaseController;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.service.SystemUserService;
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
    public Result list(@RequestParam(required = false, defaultValue = "10") int pageSize,
                       @RequestParam(required = false, defaultValue = "1") int pageNum,
                       @RequestBody(required = false) SystemUser systemUser){

        PageHelper.startPage(pageNum, pageSize);
        List<SystemUser> systemUsers = systemUserService.selectList(systemUser);
        PageInfo<SystemUser> pageInfo = new PageInfo<>(systemUsers);
        return page(pageInfo);
    }

    @GetMapping("get/{id}")
    public Result getUserById(@PathVariable Long id){
        if(id == null || id < 0){
           return fail("id有误, 请重试.");
        }
        return success(systemUserService.selectUserById(id));
    }
}
