package com.serliunx.varytalk.system.controller;

import com.serliunx.varytalk.common.annotation.Logger;
import com.serliunx.varytalk.common.annotation.PermissionRequired;
import com.serliunx.varytalk.common.base.BaseController;
import com.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.system.entity.SystemFile;
import com.serliunx.varytalk.system.service.SystemFileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class SystemFileController extends BaseController {

    private final SystemFileService systemFileService;

    public SystemFileController(SystemFileService systemFileService,
                                SystemAutoConfigurer systemAutoConfigurer) {
        this.systemFileService = systemFileService;
    }

    @PostMapping("upload")
    @PermissionRequired("system.file.upload")
    @Logger(opName = "文件管理", value = "用户上传文件")
    public Result upload(@RequestParam(value = "file")MultipartFile multipartFile){
        SystemFile systemFile = systemFileService.uploadFile(multipartFile);
        if(systemFile == null){
            return fail();
        }
        return success(systemFile);
    }

    @GetMapping("download")
    public void download(HttpServletResponse response){

    }
}
