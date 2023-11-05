package com.serliunx.varytalk.security.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ChangePasswordQuery {

    @NotEmpty(message = "密码不能为空!")
    @Length(min = 8, max = 20, message = "密码长度必须不符合要求!")
    private String newPassword;

    @NotEmpty(message = "旧密码不能为空!")
    private String oldPassword;
}
