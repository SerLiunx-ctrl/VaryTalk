package com.serliunx.varytalk.common.exception;

public class ServiceException extends RuntimeException{
    private int status;

    public ServiceException(int status) {
        this.status = status;
    }

    public ServiceException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
