package github.serliunx.varytalk.project.system.service;

import github.serliunx.varytalk.project.system.entity.SystemFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SystemFileService {

    List<SystemFile> selectList(SystemFile systemFile);

    SystemFile uploadFile(MultipartFile multipartFile);

    Long insertFile(SystemFile systemFile);
}
