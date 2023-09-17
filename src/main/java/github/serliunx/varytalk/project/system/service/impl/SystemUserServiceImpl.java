package github.serliunx.varytalk.project.system.service.impl;

import github.serliunx.varytalk.common.annotation.SetOperator;
import github.serliunx.varytalk.common.base.LoginUser;
import github.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.common.util.RedisUtils;
import github.serliunx.varytalk.common.util.SecurityUtils;
import github.serliunx.varytalk.common.util.ServletUtils;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.mapper.SystemUserMapper;
import github.serliunx.varytalk.project.system.service.SystemUserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("SystemUserService")
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserMapper systemUserMapper;
    private final RedisUtils redisUtils;
    private final SystemAutoConfigurer systemAutoConfigurer;

    public SystemUserServiceImpl(SystemUserMapper systemUserMapper,
                                 RedisUtils redisUtils,
                                 SystemAutoConfigurer systemAutoConfigurer) {

        this.systemUserMapper = systemUserMapper;
        this.redisUtils = redisUtils;
        this.systemAutoConfigurer = systemAutoConfigurer;
    }

    @Override
    public List<SystemUser> selectList(SystemUser systemUser) {
        return systemUserMapper.selectList(systemUser);
    }

    @Override
    public SystemUser selectUserById(Long id) {
        return systemUserMapper.selectUserById(id);
    }

    @Override
    public SystemUser selectUserByUsername(String username) {
        return systemUserMapper.checkUserByUsername(username);
    }

    @Override
    public boolean checkUserByUsername(String username) {
        SystemUser systemUser = systemUserMapper.checkUserByUsername(username);
        return systemUser != null;
    }

    @Override
    public boolean checkUserByPhoneNumber(String phoneNumber) {
        SystemUser systemUser = systemUserMapper.checkUserByPhoneNumber(phoneNumber);
        return systemUser != null;
    }

    @Override
    public boolean checkUserByEmail(String email) {
        SystemUser systemUser = systemUserMapper.checkUserByEmail(email);
        return systemUser != null;
    }

    @Override
    public Result loginUser(LoginUser user) {
        user.setLoginIp(ServletUtils.getIp());
        user.setClient(ServletUtils.getAgent());
        user.setLoginTime(new Date());
        this.redisUtils.put(systemAutoConfigurer.getRedisPrefix().getOnlineUsers() + user.getUsername(), user,
                systemAutoConfigurer.getTokenExpireHour() * 3600);
        //设置缓存
        return Result.success(user);
    }

    @Override
    public void registerUser(SystemUser systemUser) {
        systemUser.setPassword(SecurityUtils.generateMD5Message(systemUser.getPassword()));
        systemUserMapper.insertUser(systemUser);
    }

    @Override
    @SetOperator(SystemUser.class)
    public void insertUser(SystemUser systemUser) {
        systemUser.setPassword(SecurityUtils.generateMD5Message(systemUser.getPassword()));
        systemUserMapper.insertUser(systemUser);
    }

    @Override
    public String checkUserInformation(SystemUser systemUser) {
        if(checkUserByUsername(systemUser.getUsername())){
            return "该用户名已被使用, 请更换一个用户名试试.";
        }
        if(checkUserByEmail(systemUser.getEmail())){
            return "该邮箱已被其它用户绑定, 换一个试试.";
        }
        if(checkUserByPhoneNumber(systemUser.getPhoneNumber())){
            return "该手机号已被其它用户绑定, 换一个试试.";
        }
        return null;
    }

    @Override
    public Set<LoginUser> getOnlineUser() {
        Set<String> keys = redisUtils.keys(systemAutoConfigurer.getRedisPrefix().getOnlineUsers() + "*");
        Set<LoginUser> users = new HashSet<>();
        for (String key : keys) {
            users.add((LoginUser) redisUtils.get(key));
        }
        return users;
    }

    @Override
    @SetOperator(value = SystemUser.class, fieldName = "updateBy")
    public void updateRole(SystemUser systemUser) {
        systemUserMapper.updateRole(systemUser);
    }
}
