package github.serliunx.varytalk.project.system.controller;

import github.serliunx.varytalk.common.annotation.Logger;
import github.serliunx.varytalk.common.annotation.PermissionRequired;
import github.serliunx.varytalk.common.base.BaseController;
import github.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import github.serliunx.varytalk.common.result.Result;
import github.serliunx.varytalk.project.system.entity.SystemFile;
import github.serliunx.varytalk.project.system.service.SystemFileService;
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
