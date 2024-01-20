package com.serliunx.varytalk.framework.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Getter
@Setter
public class LoginUser {

    /**
     * 用户id(数据库字段)
     */
    private Long id;

    /**
     * 用户账户名称(唯一)
     */
    @NotEmpty(message = "账户名称不能为空!")
    @Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "用户名格式不符合要求! 只允许下划线_、数字0~9及大小写字母")
    @Length(min = 4, max = 25, message = "用户名称长度必须为4到25个字符")
    private String username;

    /**
     * 用户密码
     */
    @NotEmpty(message = "账户密码不能为空!")
    @Length(min = 8, max = 20, message = "密码长度必须不符合要求!")
    private String password;

    /**
     * 验证码
     */
    @NotEmpty(message = "验证码不能为空!")
    private String captcha;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /**
     * 操作系统
     */
    private String client;

    /**
     * 登录token
     */
    private String token;
}
