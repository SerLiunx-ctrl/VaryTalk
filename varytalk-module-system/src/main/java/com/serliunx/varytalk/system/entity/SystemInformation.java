package com.serliunx.varytalk.system.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 系统信息
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class SystemInformation {
    private String javaVersion;
    private String osName;
    private String userHome;
}
