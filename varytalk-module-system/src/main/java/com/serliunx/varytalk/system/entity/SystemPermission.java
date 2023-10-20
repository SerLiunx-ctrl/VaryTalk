package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@JsonPropertyOrder({"id", "value", "nodeName"})
public class SystemPermission extends BaseEntity {
    private Long id;

    @Length(min = 4, max = 60, message = "权限节点值长度必须为4到25个字符")
    @NotEmpty(message = "权限节点值不能为空!")
    private String value;

    @NotEmpty(message = "权限节点名称不能为空!")
    private String nodeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
