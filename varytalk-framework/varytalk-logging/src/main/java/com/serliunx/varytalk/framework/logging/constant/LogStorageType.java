package com.serliunx.varytalk.framework.logging.constant;

/**
 * 日志存储方式
 * @author SerLiunx
 * @since 1.0
 */
public enum LogStorageType {

    /**
     * 文件的形式存储日志(普通文本)
     */
    FILE_PLAIN_TEXT,

    /**
     * 以EXCEL表格的方式存储日志
     */
    FILE_EXCEL,

    /**
     * 将日志存储到MYSQL数据库中
     */
    MYSQL,

    /**
     * 将日志存储到REDIS缓存中
     */
    REDIS,

    /**
     * 将日志存储到ES中
     */
    ELASTIC_SEARCH
}
