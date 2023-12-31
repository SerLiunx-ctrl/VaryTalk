package com.serliunx.varytalk.system.aop.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件信息
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class FileContextBody {
    private String fileName;
    private Long fileSize;
}
