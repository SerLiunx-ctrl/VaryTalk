package com.serliunx.varytalk.system.service;

import com.serliunx.varytalk.system.entity.SystemFile;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SystemFileService {

    List<SystemFile> selectList(SystemFile systemFile);

    SystemFile uploadFile(MultipartFile multipartFile);

    void downLoadFile(String fileName, HttpServletResponse response);

    Long insertFile(SystemFile systemFile);
}
