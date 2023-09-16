package github.serliunx.varytalk.project.system.mapper;

import github.serliunx.varytalk.project.system.entity.SystemRolePermission;
import github.serliunx.varytalk.project.system.entity.SystemUserPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemRolePermissionMapper {
    List<SystemRolePermission> selectList(SystemRolePermission systemRolePermission);

    SystemRolePermission checkIfGiven(Long roleId, Long permissionId);

    List<SystemRolePermission> selectByRoleId(Long roleId);

    Long insertRolePermission(SystemRolePermission systemRolePermission);
}
