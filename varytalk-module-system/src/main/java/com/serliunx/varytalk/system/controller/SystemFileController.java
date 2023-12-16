package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.annotation.PermitAll;
import com.serliunx.varytalk.common.annotation.RateLimiter;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.framework.security.annotation.ApiValidation;
import com.serliunx.varytalk.framework.security.group.defaultgroup.PermissionGroup;
import com.serliunx.varytalk.system.entity.SystemFile;
import com.serliunx.varytalk.system.service.SystemFileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class SystemFileController extends BaseController {

    private final SystemFileService systemFileService;

    public SystemFileController(SystemFileService systemFileService) {
        this.systemFileService = systemFileService;
    }

    @PostMapping("upload")
    @ApiValidation(value = "system.file.upload", group = PermissionGroup.class)
    @Logger(opName = "文件管理", value = "用户上传文件")
    @RateLimiter(count = 1)
    public Result upload(@RequestParam(value = "file")MultipartFile multipartFile){
        SystemFile systemFile = systemFileService.uploadFile(multipartFile);
        if(systemFile == null){
            return fail();
        }
        return success(systemFile);
    }

    /**
     * 文件下载接口
     */
    @GetMapping("download")
    @PermitAll
    @RateLimiter(count = 1)
    public void download(HttpServletResponse response, String fileName){
        systemFileService.downLoadFile(fileName, response);
    }
}
