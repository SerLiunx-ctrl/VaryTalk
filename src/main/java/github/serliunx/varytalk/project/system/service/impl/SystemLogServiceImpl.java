package github.serliunx.varytalk.project.system.service.impl;

import github.serliunx.varytalk.project.system.entity.SystemLog;
import github.serliunx.varytalk.project.system.mapper.SystemLogMapper;
import github.serliunx.varytalk.project.system.service.SystemLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogServiceImpl implements SystemLogService {

    private final SystemLogMapper systemLogMapper;

    public SystemLogServiceImpl(SystemLogMapper systemLogMapper) {
        this.systemLogMapper = systemLogMapper;
    }

    @Override
    public List<SystemLog> selectList(SystemLog systemLog) {
        return systemLogMapper.selectList(systemLog);
    }

    @Override
    public void insertLog(SystemLog systemLog) {
        systemLogMapper.insertLog(systemLog);
    }

    @Override
    public List<SystemLog> selectByUserId(Long userId) {
        return systemLogMapper.selectByUserId(userId);
    }
}
