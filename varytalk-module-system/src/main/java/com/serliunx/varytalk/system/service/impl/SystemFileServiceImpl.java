package com.serliunx.varytalk.system.service.impl;

import com.serliunx.varytalk.framework.core.config.SystemAutoConfigurer;
import com.serliunx.varytalk.framework.core.exception.ServiceException;
import com.serliunx.varytalk.framework.core.tool.SecurityUtils;
import com.serliunx.varytalk.system.entity.SystemFile;
import com.serliunx.varytalk.system.mapper.SystemFileMapper;
import com.serliunx.varytalk.system.service.SystemFileService;
import com.serliunx.varytalk.system.service.SystemUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SystemFileServiceImpl implements SystemFileService {

    private final SystemFileMapper systemFileMapper;
    private final SystemUserService systemUserService;
    private final SystemAutoConfigurer systemAutoConfigurer;

    public SystemFileServiceImpl(SystemFileMapper systemFileMapper,
                                 SystemUserService systemUserService,
                                 SystemAutoConfigurer systemAutoConfigurer) {
        this.systemFileMapper = systemFileMapper;
        this.systemUserService = systemUserService;
        this.systemAutoConfigurer = systemAutoConfigurer;
    }

    @Override
    public List<SystemFile> selectList(SystemFile systemFile) {
        return systemFileMapper.selectList(systemFile);
    }

    @Override
    public SystemFile uploadFile(MultipartFile multipartFile) {
        String name = UUID.randomUUID() + "u" + SecurityUtils.getUserId() + "r" + SecurityUtils.getRoleId();
        String path = generateFullPath(systemAutoConfigurer.getFileInfo().getUploadPath())
                .replace("/", File.separator);
        File dir = new File(path);
        if(!dir.exists()){
            if(!dir.mkdirs()){
                throw new ServiceException("文件上传出错, 请联系管理员!", 400);
            }
        }
        SystemFile systemFile = new SystemFile();
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            if(originalFilename == null){
                throw new ServiceException("文件上传出错, 请联系管理员!", 400);
            }
            String extensionName = getExtension(originalFilename);
            File toStore = new File(dir + File.separator + name + "." + extensionName);
            multipartFile.transferTo(toStore);
            systemFile.setFileSize(multipartFile.getSize());
            systemFile.setName(name + "." + extensionName);
            systemFile.setPath(path + File.separator + name + "." + extensionName);
            systemFile.setOriginalName(originalFilename);
            systemFile.setUserId(SecurityUtils.getUserId());
        }catch (Exception e){
            throw new ServiceException("文件上传出错, 请联系管理员!", 400);
        }
        systemFile.setCreateBy(systemUserService.selectUserByIdFlatted(SecurityUtils.getUserId()).getUsername());
        insertFile(systemFile);
        return systemFile;
    }

    @Override
    public void downloadFile(String fileName, HttpServletResponse response) {
        SystemFile systemFile = systemFileMapper.selectByName(fileName);
        if(systemFile == null){
            throw new ServiceException("文件信息不存在!", 400);
        }
        String path = systemFile.getPath();
        File found = new File(path);
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(found));
            response.setContentType("application/octet-stream");
            //使用URL编码、解决文件名为中文时会出现的问题
            String encodedFileName = URLEncoder.encode(systemFile.getOriginalName(), StandardCharsets.UTF_8);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setContentLengthLong(found.length());
            response.getOutputStream().write(inputStream.readAllBytes());
            systemFileMapper.updateCount(systemFile.getId());
        }catch (Exception e){
            throw new ServiceException(e.getMessage(), 400);
        }
    }

    @Override
    public Long insertFile(SystemFile systemFile) {
        return systemFileMapper.insertFile(systemFile);
    }

    private String getExtension(String fullFileName) {
        String extension = "";
        int i = fullFileName.lastIndexOf('.');
        if (i > 0) {
            extension = fullFileName.substring(i + 1);
        }
        return extension;
    }

    private String generateFullPath(String original){
        Date dateTimeNow = new Date();
        String years = new SimpleDateFormat("yyyy").format(dateTimeNow);
        String months = new SimpleDateFormat("MM").format(dateTimeNow);
        String days = new SimpleDateFormat("dd").format(dateTimeNow);
        return original.replace("%years%", years)
                .replace("%months%", months)
                .replace("%days%", days);
    }
}
