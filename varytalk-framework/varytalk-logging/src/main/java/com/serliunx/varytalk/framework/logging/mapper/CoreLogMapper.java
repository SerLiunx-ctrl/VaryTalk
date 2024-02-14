package com.serliunx.varytalk.framework.logging.mapper;

import com.serliunx.varytalk.framework.logging.entity.CoreLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 日志Mapper
 * @author SerLiunx
 * @since 1.0
 */
@Mapper
public interface CoreLogMapper {

    void insertLog(CoreLog coreLog);

    List<CoreLog> selectList(CoreLog coreLog);
}
