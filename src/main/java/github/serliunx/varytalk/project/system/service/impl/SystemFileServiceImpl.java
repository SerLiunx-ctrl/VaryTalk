package github.serliunx.varytalk.project.system.service.impl;

import github.serliunx.varytalk.common.annotation.SetOperator;
import github.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import github.serliunx.varytalk.common.exception.ServiceException;
import github.serliunx.varytalk.common.util.SecurityUtils;
import github.serliunx.varytalk.project.system.entity.SystemFile;
import github.serliunx.varytalk.project.system.mapper.SystemFileMapper;
import github.serliunx.varytalk.project.system.service.SystemFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class SystemFileServiceImpl implements SystemFileService {

    private final SystemFileMapper systemFileMapper;
    private final SystemAutoConfigurer systemAutoConfigurer;

    public SystemFileServiceImpl(SystemFileMapper systemFileMapper,
                                 SystemAutoConfigurer systemAutoConfigurer) {
        this.systemFileMapper = systemFileMapper;
        this.systemAutoConfigurer = systemAutoConfigurer;
    }

    @Override
    public List<SystemFile> selectList(SystemFile systemFile) {
        return systemFileMapper.selectList(systemFile);
    }

    @Override
    public SystemFile uploadFile(MultipartFile multipartFile) {
        String name = UUID.randomUUID().toString();
        File dir = new File(systemAutoConfigurer.getFileInfo().getUploadPath());
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
            systemFile.setPath(File.separator);
            systemFile.setOriginalName(originalFilename);
            systemFile.setUserId(SecurityUtils.getUserId());
        }catch (Exception e){
            throw new ServiceException("文件上传出错, 请联系管理员!", 400);
        }
        insertFile(systemFile);
        return systemFile;
    }

    @Override
    @SetOperator(SystemFile.class)
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
}
