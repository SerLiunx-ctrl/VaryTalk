package com.serliunx.varytalk.common.exception;

import lombok.Getter;

/**
 * 异常: 系统内没有该权限
 */
@Getter
public class PermissionNotFoundException extends RuntimeException{

    private final String permissionValue;

    public PermissionNotFoundException(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public PermissionNotFoundException(String message, String permissionValue) {
        super(message);
        this.permissionValue = permissionValue;
    }
}
