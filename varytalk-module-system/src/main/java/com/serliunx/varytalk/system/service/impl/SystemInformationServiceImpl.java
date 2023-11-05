package com.serliunx.varytalk.system.service.impl;

import com.serliunx.varytalk.system.entity.SystemInformation;
import com.serliunx.varytalk.system.service.SystemInformationService;
import org.springframework.stereotype.Service;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Service
public class SystemInformationServiceImpl implements SystemInformationService {

    @Override
    public SystemInformation getSystem() {
        return init();
    }

    private SystemInformation init(){
        return new SystemInformation()
                .setJavaVersion(System.getProperty("java.version"))
                .setOsName(System.getProperty("os.name"))
                .setUserHome(System.getProperty("user.home"));
    }
}
