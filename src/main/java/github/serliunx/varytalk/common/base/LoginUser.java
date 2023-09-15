package github.serliunx.varytalk.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
