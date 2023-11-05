package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id", "userId", "originalName", "name", "path", "fileSize"})
public class SystemFile extends BaseEntity {

    private Long id;
    private Long userId;
    private String originalName;
    private String name;
    private String path;
    private Long fileSize;
}
