package github.serliunx.varytalk.project.system.service.impl;

import github.serliunx.varytalk.project.system.entity.SystemRole;
import github.serliunx.varytalk.project.system.mapper.SystemRoleMapper;
import github.serliunx.varytalk.project.system.service.SystemRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    private final SystemRoleMapper systemRoleMapper;

    public SystemRoleServiceImpl(SystemRoleMapper systemRoleMapper) {
        this.systemRoleMapper = systemRoleMapper;
    }

    @Override
    public List<SystemRole> selectList(SystemRole systemRole) {
        return systemRoleMapper.selectList(systemRole);
    }

    @Override
    public Long insertRole(SystemRole systemRole) {
        return systemRoleMapper.insertRole(systemRole);
    }

    @Override
    public SystemRole selectById(Long id) {
        return systemRoleMapper.selectById(id);
    }

    @Override
    public SystemRole selectByName(String roleName) {
        return systemRoleMapper.selectByName(roleName);
    }
}
