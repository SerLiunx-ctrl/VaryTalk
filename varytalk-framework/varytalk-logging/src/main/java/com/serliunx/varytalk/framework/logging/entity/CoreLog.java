package com.serliunx.varytalk.framework.logging.entity;

import com.serliunx.varytalk.framework.core.entity.base.BaseEntity;
import com.serliunx.varytalk.framework.logging.constant.LogLevel;
import com.serliunx.varytalk.framework.logging.constant.OperatorType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 日志实体
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class CoreLog extends BaseEntity {

    /**
     * 日志记录ID
     */
    private Long id;

    /**
     * 日志描述
     */
    private String description;

    /**
     * 日志级别(不建议模糊查询、且默认不支持)
     */
    private LogLevel level;

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求体
     */
    private String requestBody;

    /**
     * 操作者id(操作者类型为系统时该值为-1, 否则为用户的真实id)
     */
    private Long operatorId;

    /**
     * 操作者ip(操作者类型为系统时该值为空, 否则为用户的真实ip)
     */
    private String operatorIp;

    /**
     * 操作者类型(普通用户、系统等。不建议模糊查询、且默认不支持)
     */
    private OperatorType operatorType;
}
