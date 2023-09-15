package github.serliunx.varytalk.project.system.service.impl;

import github.serliunx.varytalk.project.system.entity.SystemPermission;
import github.serliunx.varytalk.project.system.mapper.SystemPermissionMapper;
import github.serliunx.varytalk.project.system.service.SystemPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemPermissionServiceImpl implements SystemPermissionService {

    private final SystemPermissionMapper systemPermissionMapper;

    public SystemPermissionServiceImpl(SystemPermissionMapper systemPermissionMapper) {
        this.systemPermissionMapper = systemPermissionMapper;
    }

    @Override
    public List<SystemPermission> selectList(SystemPermission systemPermission) {
        return systemPermissionMapper.selectList(systemPermission);
    }

    @Override
    public Long insertPermission(SystemPermission systemPermission) {
        return systemPermissionMapper.insertPermission(systemPermission);
    }

    @Override
    public SystemPermission selectByValue(String value) {
        return systemPermissionMapper.selectByValue(value);
    }

    @Override
    public SystemPermission selectByName(String nodeName) {
        return systemPermissionMapper.selectByName(nodeName);
    }

    @Override
    public SystemPermission selectById(Long id) {
        return systemPermissionMapper.selectById(id);
    }
}
