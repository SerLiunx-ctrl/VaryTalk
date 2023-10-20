package com.serliunx.varytalk.system.mapper;

import com.serliunx.varytalk.system.entity.SystemLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SystemLogMapper {
    List<SystemLog> selectList(SystemLog systemLog);

    void insertLog(SystemLog systemLog);

    List<SystemLog> selectByUserId(Long userId);
}
