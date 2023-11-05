package com.serliunx.varytalk.schedule;

import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;

/**
 * @author SerLiunx
 * @since 1.0
 */
public abstract class BaseSchedule {

    protected final SystemAutoConfigurer systemAutoConfigurer;

    public BaseSchedule(SystemAutoConfigurer systemAutoConfigurer) {
        this.systemAutoConfigurer = systemAutoConfigurer;
    }
}
