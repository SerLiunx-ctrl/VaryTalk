package com.serliunx.varytalk.common.exception;

import lombok.Getter;

/**
 * 鉴权冲突异常
 * @author SerLiunx
 * @since 1.0
 */
@Getter
public class AuthenticationConflictException extends RuntimeException{

    private final String methodFullName;

    public AuthenticationConflictException(String methodFullName) {
        super();
        this.methodFullName = methodFullName;
    }

    public AuthenticationConflictException(String message, String methodFullName) {
        super(message);
        this.methodFullName = methodFullName;
    }
}
