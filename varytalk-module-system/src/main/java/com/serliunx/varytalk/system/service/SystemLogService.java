package com.serliunx.varytalk.system.service;

import com.serliunx.varytalk.system.entity.SystemLog;

import java.util.List;

public interface SystemLogService {
    List<SystemLog> selectList(SystemLog systemLog);

    void insertLog(SystemLog systemLog);

    List<SystemLog> selectByUserId(Long userId);
}
