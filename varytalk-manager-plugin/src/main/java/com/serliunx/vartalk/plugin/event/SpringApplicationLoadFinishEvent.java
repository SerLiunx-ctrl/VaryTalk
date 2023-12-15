package com.serliunx.vartalk.plugin.event;

import lombok.Getter;
import org.springframework.context.ApplicationContext;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Getter
public class SpringApplicationLoadFinishEvent extends SpringApplicationEvent{

    private final ApplicationContext applicationContext;

    public SpringApplicationLoadFinishEvent(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
