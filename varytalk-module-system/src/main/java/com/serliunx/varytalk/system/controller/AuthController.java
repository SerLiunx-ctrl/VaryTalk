package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.framework.core.annotation.PermitAll;
import com.serliunx.varytalk.framework.core.entity.base.BaseController;
import com.serliunx.varytalk.framework.core.entity.base.LoginUser;
import com.serliunx.varytalk.framework.core.config.SystemAutoConfigurer;
import com.serliunx.varytalk.framework.core.exception.ServiceException;
import com.serliunx.varytalk.framework.core.entity.result.Result;
import com.serliunx.varytalk.framework.core.tool.JwtUtils;
import com.serliunx.varytalk.framework.core.tool.RedisUtils;
import com.serliunx.varytalk.framework.core.tool.SecurityUtils;
import com.serliunx.varytalk.system.entity.SystemUser;
import com.serliunx.varytalk.system.entity.query.ChangePasswordQuery;
import com.serliunx.varytalk.system.entity.resp.CaptchaCode;
import com.serliunx.varytalk.system.service.CaptchaService;
import com.serliunx.varytalk.system.service.SystemUserService;
import com.serliunx.varytalk.system.validation.system.SystemUserRegisterGroup;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("auth")
public class AuthController extends BaseController {

    private final SystemUserService systemUserService;
    private final SystemAutoConfigurer systemAutoConfigurer;
    private final CaptchaService captchaService;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    public AuthController(SystemUserService systemUserService,
                          SystemAutoConfigurer systemAutoConfigurer,
                          CaptchaService captchaService, JwtUtils jwtUtils,
                          RedisUtils redisUtils) {
        this.systemUserService = systemUserService;
        this.systemAutoConfigurer = systemAutoConfigurer;
        this.captchaService = captchaService;
        this.jwtUtils = jwtUtils;
        this.redisUtils = redisUtils;
    }

    @PostMapping("login")
    public Result login(@RequestBody @Validated LoginUser user){
        String uuid = user.getUuid();
        if(uuid == null || uuid.isEmpty()){
            return fail("验证码错误!");
        }
        String code = captchaService.getCode(uuid);
        String captcha = user.getCaptcha();
        if(code == null || code.isEmpty() || !code.equals(captcha)){
            return fail("验证码错误!");
        }
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
        captchaService.deleteCode(uuid);
        return systemUserService.loginUser(user);
    }

    @PostMapping("register")
    public Result register(@RequestBody @Validated(SystemUserRegisterGroup.class) SystemUser user){
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

    @PostMapping("logout")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader(systemAutoConfigurer.getAuthHeader());
        String key = systemAutoConfigurer.getRedisPrefix().getMainPrefix() + systemAutoConfigurer.getRedisPrefix().getOnlineUsers() + jwtUtils.getUsername(token);
        LoginUser loginUser = redisUtils.get(key, LoginUser.class);
        if(loginUser == null){
            return fail("操作失败, 用户未登录!");
        }
        return success(redisUtils.delete(key), "注销成功!");
    }

    @PermitAll
    @GetMapping("captcha")
    public Result captcha(){
        String uuid = UUID.randomUUID().toString();
        CaptchaCode captchaCode = new CaptchaCode();
        captchaCode.setUuid(uuid);
        captchaCode.setCaptchaCode(captchaService.generateCode(uuid));
        return success(captchaCode, "成功获取验证码!");
    }
}
