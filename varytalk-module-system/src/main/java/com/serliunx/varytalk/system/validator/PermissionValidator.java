package com.serliunx.varytalk.system.validator;

import com.serliunx.varytalk.security.support.ValidationContext;
import com.serliunx.varytalk.security.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 权限节点值校验器
 * @author SerLiunx
 * @since 1.0
 */
@Slf4j
@Component
public class PermissionValidator implements Validator {

    @Override
    public boolean preValidate(ValidationContext validationContext) {
        log.info("a singleton validator processed! {}", validationContext.toString());
        return true;
    }

    @Override
    public boolean toChain() {
        return false;
    }
}
