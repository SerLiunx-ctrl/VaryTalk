package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.api.system.entity.Permission;
import com.serliunx.varytalk.framework.core.entity.base.BaseEntity;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@JsonPropertyOrder({"id", "value", "nodeName"})
public class SystemPermission extends BaseEntity implements Permission {

    private Long id;

    @Length(min = 4, max = 60, message = "权限节点值长度必须为4到25个字符")
    @NotEmpty(message = "权限节点值不能为空!")
    private String value;

    @NotEmpty(message = "权限节点名称不能为空!")
    private String nodeName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getNodeName() {
        return nodeName;
    }

    @Override
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
