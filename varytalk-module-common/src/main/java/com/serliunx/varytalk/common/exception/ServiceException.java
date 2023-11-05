package com.serliunx.varytalk.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends RuntimeException{
    private int status;

    public ServiceException(int status) {
        this.status = status;
    }

    public ServiceException(String message, int status) {
        super(message);
        this.status = status;
    }
}
