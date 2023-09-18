package github.serliunx.varytalk.project.system.controller;

import github.serliunx.varytalk.common.annotation.PermissionRequired;
import github.serliunx.varytalk.common.base.BaseController;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.project.system.entity.SystemLog;
import github.serliunx.varytalk.project.system.service.SystemLogService;
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
    @PermissionRequired("system.log.list")
    public Result list(SystemLog systemLog){
        startPage();
        List<SystemLog> systemLogs = systemLogService.selectList(systemLog);
        return page(systemLogs);
    }
}
