package com.serliunx.varytalk.api.system.entity;

/**
 * 系统模块 - 权限
 * @author SerLiunx
 * @since 1.0
 */
public interface Permission {

    Long getId();

    void setId(Long id);

    String getValue();

    void setValue(String value);

    String getNodeName();

    void setNodeName(String nodeName);
}
