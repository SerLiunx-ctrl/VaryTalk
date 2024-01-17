package com.serliunx.varytalk.system.mapper;

import com.serliunx.varytalk.system.entity.SystemFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemFileMapper {

    List<SystemFile> selectList(SystemFile systemFile);

    Long insertFile(SystemFile systemFile);

    SystemFile selectByName(String name);

    void updateCount(Long id);
}
