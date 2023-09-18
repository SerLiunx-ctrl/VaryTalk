package github.serliunx.varytalk.project.system.service;

import github.serliunx.varytalk.project.system.entity.SystemLog;

import java.util.List;

public interface SystemLogService {
    List<SystemLog> selectList(SystemLog systemLog);

    void insertLog(SystemLog systemLog);

    List<SystemLog> selectByUserId(Long userId);
}
