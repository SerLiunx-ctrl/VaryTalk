package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.framework.core.annotation.RateLimiter;
import com.serliunx.varytalk.framework.core.entity.base.BaseController;
import com.serliunx.varytalk.framework.core.entity.result.Result;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
import com.serliunx.varytalk.system.entity.SystemLog;
import com.serliunx.varytalk.system.service.SystemLogService;
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
    @ApiValidation(value = "system.log.list", group = PermissionGroup.class)
    public Result list(SystemLog systemLog){
        startPage();
        List<SystemLog> systemLogs = systemLogService.selectList(systemLog);
        return page(systemLogs);
    }
}
