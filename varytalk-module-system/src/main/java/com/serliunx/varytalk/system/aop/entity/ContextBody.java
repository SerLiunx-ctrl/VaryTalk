package com.serliunx.varytalk.system.aop.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 未知请求体的参数
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class ContextBody {

    private String type;
    private Object body;

    public static final String TYPE_FILE = "file";

    /**
     * 快速生成文件日志信息
     * @param file 文件
     * @return 日志信息(JSON)
     */
    public static String fromFile(MultipartFile file){
        ObjectMapper mapper = new ObjectMapper();
        ContextBody body = new ContextBody();
        FileContextBody fileContextBody = new FileContextBody();
        fileContextBody.setFileSize(file.getSize());
        fileContextBody.setFileName(file.getOriginalFilename());
        body.setBody(fileContextBody);
        body.setType(TYPE_FILE);
        try {
            return mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
