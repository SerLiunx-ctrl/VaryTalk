package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.common.base.BaseEntity;
import com.serliunx.varytalk.common.validation.system.SystemUserInsertGroup;
import com.serliunx.varytalk.common.validation.system.SystemUserRegisterGroup;
import com.serliunx.varytalk.common.validation.system.SystemUserRoleUpdateGroup;
import com.serliunx.varytalk.common.validation.system.SystemUserUpdateGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@JsonPropertyOrder({"id", "username", "password", "phoneNumber", "email", "nickName", "sex", "roleId", "role"})
public class SystemUser extends BaseEntity {

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
    private String password;

    @Length(min = 8, max = 12, message = "手机号码长度必须为8位及以上",
            groups = {SystemUserInsertGroup.class, SystemUserUpdateGroup.class})
    private String phoneNumber;

    private String email;

    private String nickName;

    private String sex;

    @NotNull(message = "角色ID不能为空!", groups = SystemUserRoleUpdateGroup.class)
    private Long roleId;

    private SystemRole role;
}
