package github.serliunx.varytalk.project.system.mapper;

import github.serliunx.varytalk.project.system.entity.SystemFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemFileMapper {
    List<SystemFile> selectList(SystemFile systemFile);

    Long insertFile(SystemFile systemFile);
}
