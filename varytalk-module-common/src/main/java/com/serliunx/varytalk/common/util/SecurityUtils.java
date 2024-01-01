package com.serliunx.varytalk.common.util;

import org.springframework.util.DigestUtils;

import java.util.Map;

public class SecurityUtils {

    private static final ThreadLocal<Map<String, Object>> infoMap = new ThreadLocal<>();

    private SecurityUtils(){}

    /**
     * 对输入的字符串加密
     * <p>
     * 使用的是 Spring 自带的{@link DigestUtils}
     * @param source 原字符串
     * @return 加密后的字符串
     */
    public static String generateMD5Message(String source){
        return DigestUtils.md5DigestAsHex(source.getBytes());
    }

    /**
     * 设置当前用户的信息
     * @param info 信息
     */
    public static void setUserInfo(Map<String, Object> info){
        infoMap.set(info);
    }

    /**
     * 获取当前用户的id
     * @return 用户id
     */
    public static Long getUserId(){
        return (Long) infoMap.get().get("userId");
    }

    /**
     * 获取当前用户的用户名
     * @return 用户名
     */
    public static String getUsername(){
        return infoMap.get().get("username").toString();
    }

    /**
     * 获取当前用户的角色id
     * @return 角色id
     */
    public static Long getRoleId(){
        return (Long)infoMap.get().get("roleId");
    }

    /**
     * 移除临时用户信息
     */
    public static void clear(){
        infoMap.remove();
    }
}
