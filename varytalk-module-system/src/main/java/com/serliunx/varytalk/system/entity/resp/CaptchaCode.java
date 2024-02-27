package com.serliunx.varytalk.system.entity.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class CaptchaCode {
    private String uuid;
    private String captchaCode;
}
