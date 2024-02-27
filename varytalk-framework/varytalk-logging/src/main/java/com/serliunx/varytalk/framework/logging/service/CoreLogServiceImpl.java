package com.serliunx.varytalk.framework.logging.service;

import com.serliunx.varytalk.framework.logging.mapper.CoreLogMapper;
import org.springframework.stereotype.Service;

/**
 * 日志服务实现
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class CoreLogServiceImpl implements CoreLogService{
    private final CoreLogMapper coreLogMapper;

    public CoreLogServiceImpl(CoreLogMapper coreLogMapper) {
        this.coreLogMapper = coreLogMapper;
    }

    //
}
