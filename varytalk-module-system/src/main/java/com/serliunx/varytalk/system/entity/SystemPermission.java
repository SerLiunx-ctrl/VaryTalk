package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@JsonPropertyOrder({"id", "value", "nodeName"})
public class SystemPermission extends BaseEntity {
    private Long id;

    @Length(min = 4, max = 60, message = "权限节点值长度必须为4到25个字符")
    @NotEmpty(message = "权限节点值不能为空!")
    private String value;

    @NotEmpty(message = "权限节点名称不能为空!")
    private String nodeName;
}
