package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.common.annotation.RateLimiter;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.annotation.ApiValidations;
import com.serliunx.varytalk.system.entity.SystemLog;
import com.serliunx.varytalk.system.service.SystemLogService;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("system-log")
public class SystemLogController extends BaseController {

    private final SystemLogService systemLogService;

    public SystemLogController(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    @GetMapping("list")
    @RateLimiter(time = 1, count = 1)
    @ApiValidations({
            @ApiValidation(value = "system.user.add", group = PermissionGroup.class),
            @ApiValidation(value = "system.user.add", group = PermissionGroup.class)
    })
    public Result list(SystemLog systemLog){
        startPage();
        List<SystemLog> systemLogs = systemLogService.selectList(systemLog);
        return page(systemLogs);
    }
}
