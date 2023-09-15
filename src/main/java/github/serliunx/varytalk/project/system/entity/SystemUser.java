package github.serliunx.varytalk.project.system.entity;

import github.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class SystemUser extends BaseEntity {

    private Long id;

    @NotEmpty(message = "账户名称不能为空!")
    @Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "用户名格式不符合要求! 只允许下划线_、数字0~9及大小写字母")
    @Length(min = 4, max = 25, message = "用户名称长度必须为4到25个字符")
    private String username;

    @NotEmpty(message = "账户密码不能为空!")
    @Length(min = 8, max = 20, message = "密码长度必须不符合要求!")
    private String password;

    @Length(min = 8, max = 12, message = "手机号码长度必须为8位及以上")
    private String phoneNumber;

    private String email;

    private String nickName;

    private String sex;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
