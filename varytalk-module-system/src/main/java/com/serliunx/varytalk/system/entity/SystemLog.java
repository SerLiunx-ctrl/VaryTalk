package com.serliunx.varytalk.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;

@Getter
public class SystemLog {

    private SystemLog(){}

    /**
     * 日志id
     */
    private Long id;

    /**
     * 执行该操作的用户id
     */
    private Long userId;

    /**
     * 执行该操作的用户名称
     */
    private String username;

    /**
     * 操作时的ip地址
     */
    private String ipAddress;

    /**
     * 该操作的接口路径
     */
    private String apiPath;

    /**
     * 操作名称&类型
     */
    private String opName;

    /**
     * 操作的具体信息
     */
    private String opContext;

    /**
     * 请求体
     */
    private String opBody;

    /**
     * 请求参数
     */
    private String opParams;

    /**
     * 执行的操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date opTime;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public void setOpContext(String opContext) {
        this.opContext = opContext;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public void setOpBody(String opBody) {
        this.opBody = opBody;
    }

    public void setOpParams(String opParams) {
        this.opParams = opParams;
    }

    public static Builder getBuilder() {
        return new Builder(new SystemLog());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static class Builder{
        private final SystemLog obj;

        public Builder(SystemLog obj) {
            this.obj = obj;
        }

        public Builder setId(Long id){
            obj.setId(id);
            return this;
        }

        public Builder setUserId(Long userId){
            obj.setUserId(userId);
            return this;
        }

        public Builder setIpAddress(String ipAddress){
            obj.setIpAddress(ipAddress);
            return this;
        }

        public Builder setApiPath(String apiPath){
            obj.setApiPath(apiPath);
            return this;
        }

        public Builder setOpName(String opName){
            obj.setOpName(opName);
            return this;
        }

        public Builder setOpContext(String opContext){
            obj.setOpContext(opContext);
            return this;
        }

        public Builder setOpBody(String opBody){
            obj.setOpBody(opBody);
            return this;
        }

        public Builder setParams(String opParams){
            obj.setOpParams(opParams);
            return this;
        }

        public Builder setOpTime(Date opTime){
            obj.setOpTime(opTime);
            return this;
        }

        public Builder now(){
            obj.setOpTime(new Date());
            return this;
        }

        public SystemLog build(){
            return obj;
        }
    }
}
