package github.serliunx.varytalk.project.system.mapper;

import github.serliunx.varytalk.project.system.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemUserMapper {

    List<SystemUser> selectList(SystemUser systemUser);

    SystemUser checkUserByUsername(String username);

    SystemUser checkUserByPhoneNumber(String phoneNumber);

    SystemUser checkUserByEmail(String email);

    SystemUser selectUserById(Long id);

    Long insertUser(SystemUser systemUser);

    void updateUser(SystemUser systemUser);

    void updateRole(SystemUser systemUser);

    void updatePassword(SystemUser systemUser);
}
