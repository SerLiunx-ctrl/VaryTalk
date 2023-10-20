package com.serliunx.varytalk.common.exception;

/**
 * 鉴权冲突异常
 * @author SerLiunx
 * @since 1.0
 */
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

    public String getMethodFullName() {
        return methodFullName;
    }
}
