package com.serliunx.varytalk.security.exception;

/**
 * 校验器模式异常
 * @author SerLiunx
 * @since 1.0
 */
public class ModeException extends RuntimeException{

    public ModeException() {
    }

    public ModeException(String message) {
        super(message);
    }

    public ModeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModeException(Throwable cause) {
        super(cause);
    }

    public ModeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
