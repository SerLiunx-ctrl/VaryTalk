package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.serliunx.varytalk.api.system.entity.Role;
import com.serliunx.varytalk.api.system.entity.User;
import com.serliunx.varytalk.framework.core.entity.base.BaseEntity;
import com.serliunx.varytalk.framework.core.jsonserializer.SensitiveFieldSerializer;
import com.serliunx.varytalk.system.validation.system.SystemUserInsertGroup;
import com.serliunx.varytalk.system.validation.system.SystemUserRegisterGroup;
import com.serliunx.varytalk.system.validation.system.SystemUserRoleUpdateGroup;
import com.serliunx.varytalk.system.validation.system.SystemUserUpdateGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;


@JsonPropertyOrder({"id", "username", "password", "phoneNumber", "email", "nickName", "sex", "roleId", "role"})
public class SystemUser extends BaseEntity implements User {

    @NotNull(message = "用户ID不能为空!", groups = SystemUserRoleUpdateGroup.class)
    private Long id;

    @NotNull(message = "账户名称不能为空!", groups = SystemUserInsertGroup.class)
    @NotNull(message = "注册账号不能为空!", groups = SystemUserRegisterGroup.class)
    @Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "用户名格式不符合要求! 只允许下划线_、数字0~9及大小写字母", groups = SystemUserInsertGroup.class)
    @Size(min = 4, max = 25, message = "用户名称长度必须为4到25个字符",
            groups = {SystemUserInsertGroup.class, SystemUserRegisterGroup.class})
    private String username;

    @NotNull(message = "账户密码不能为空!", groups = SystemUserInsertGroup.class)
    @NotNull(message = "必须输入你要注册的密码!", groups = SystemUserRegisterGroup.class)
    @Length(min = 8, max = 20, message = "密码长度必须不符合要求!", groups = SystemUserInsertGroup.class)
    @JsonSerialize(using = SensitiveFieldSerializer.class)
    private String password;

    @Length(min = 8, max = 12, message = "手机号码长度必须为8位及以上",
            groups = {SystemUserInsertGroup.class, SystemUserUpdateGroup.class})
    @JsonSerialize(using = SensitiveFieldSerializer.class)
    private String phoneNumber;

    private String email;

    private String nickName;

    private String sex;

    @NotNull(message = "角色ID不能为空!", groups = SystemUserRoleUpdateGroup.class)
    private Long roleId;

    private Role role;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String getSex() {
        return sex;
    }

    @Override
    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public Long getRoleId() {
        return roleId;
    }

    @Override
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }
}
