package github.serliunx.varytalk.security.controller;

import github.serliunx.varytalk.common.base.BaseController;
import github.serliunx.varytalk.common.base.LoginUser;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.common.util.JwtUtils;
import github.serliunx.varytalk.common.util.SecurityUtils;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.service.SystemUserService;
import github.serliunx.varytalk.security.entity.ChangePasswordQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController extends BaseController {

    private final SystemUserService systemUserService;
    private final JwtUtils jwtUtils;

    public AuthController(SystemUserService systemUserService, JwtUtils jwtUtils) {
        this.systemUserService = systemUserService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("login")
    public Result login(@RequestBody @Validated LoginUser user){
        SystemUser userFound = systemUserService.selectUserByUsername(user.getUsername());
        if(userFound == null){
            return fail("该用户不存在!");
        }
        String passWord = SecurityUtils.generateMD5Message(user.getPassword());
        if(!userFound.getPassword().equals(passWord)){
            return fail("密码错误!");
        }
        user.setId(userFound.getId());
        String token = jwtUtils.getLoginToken(user);
        user.setPassword(passWord);
        user.setToken(token);
        user.setId(userFound.getId());
        return systemUserService.loginUser(user);
    }

    @PostMapping("register")
    public Result register(@RequestBody @Validated SystemUser user){
        String result = systemUserService.checkUserInformation(user);
        if(result != null){
            return fail(result);
        }
        systemUserService.registerUser(user);
        return success(user.getId(), "注册成功! 你现在可以登录了.");
    }

    @PutMapping("change-password")
    public Result changePassword(@Validated @RequestBody ChangePasswordQuery query){
        SystemUser systemUser = systemUserService.selectUserById(SecurityUtils.getUserId());
        if(systemUser == null){
            return fail("用户不存在, 请重试!");
        }
        if(!SecurityUtils.generateMD5Message(query.getOldPassword()).equals(systemUser.getPassword())){
            return fail("旧密码错误, 请重试!");
        }
        systemUser.setPassword(query.getNewPassword());
        systemUserService.updatePassword(systemUser);
        return success("密码修改成功!");
    }

    public Result logout(){
        return Result.success("成功注销!");
    }
}
