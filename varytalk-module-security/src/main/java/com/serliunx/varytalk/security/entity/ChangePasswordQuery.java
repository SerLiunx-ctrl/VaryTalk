package com.serliunx.varytalk.security.entity;

import com.serliunx.varytalk.common.validation.group.SystemUserInsertGroup;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class ChangePasswordQuery {

    @NotEmpty(message = "密码不能为空!")
    @Length(min = 8, max = 20, message = "密码长度必须不符合要求!", groups = SystemUserInsertGroup.class)
    private String newPassword;

    @NotEmpty(message = "旧密码不能为空!")
    private String oldPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
