package com.serliunx.varytalk.framework.logging.constant;

/**
 * 日志操作者
 * @author SerLiunx
 * @since 1.0
 */
public enum OperatorType {

    /**
     * 用户(包括管理员、普通用户等)
     */
    USER,

    /**
     * 系统(一般由方法调用且无法从上下文拿取到用户信息、比如定时任务)
     */
    SYSTEM,
    ;
}
