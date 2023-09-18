package github.serliunx.varytalk.security.service;

import github.serliunx.varytalk.common.exception.PermissionNotFoundException;
import github.serliunx.varytalk.project.system.entity.SystemPermission;
import github.serliunx.varytalk.project.system.entity.SystemRolePermission;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.entity.SystemUserPermission;
import github.serliunx.varytalk.project.system.service.SystemPermissionService;
import github.serliunx.varytalk.project.system.service.SystemRolePermissionService;
import github.serliunx.varytalk.project.system.service.SystemUserPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    private final SystemPermissionService systemPermissionService;
    private final SystemUserPermissionService systemUserPermissionService;
    private final SystemRolePermissionService systemRolePermissionService;

    public PermissionService(SystemPermissionService systemPermissionService,
                             SystemUserPermissionService systemUserPermissionService,
                             SystemRolePermissionService systemRolePermissionService) {
        this.systemPermissionService = systemPermissionService;
        this.systemUserPermissionService = systemUserPermissionService;
        this.systemRolePermissionService = systemRolePermissionService;
    }

    /**
     * 检查用户是否拥有该权限
     * @param systemUser 用户
     * @param permission 权限
     * @return 拥有返回真, 否则返回假
     */
    public boolean hasPermission(SystemUser systemUser, String permission){
        SystemPermission systemPermission = systemPermissionService.selectByValue(permission);
        if(systemPermission == null){
            throw new PermissionNotFoundException(permission);
        }
        List<SystemUserPermission> systemUserPermissions = systemUserPermissionService
                .selectByUserId(systemUser.getId());

        //第一步, 直接检查该用户是否拥有该节点, 拥有则直接返回真
        List<String> values = systemUserPermissions.stream()
                .map(SystemUserPermission::getPermissionValue).toList();
        if(values.contains(permission)){
            return true;
        }
        //第二步, 检查用户所属角色是否拥有该节点, 拥有则直接返回真
        List<String> rolePermissions = systemRolePermissionService.selectByRoleId(systemUser.getRoleId()).stream()
                .map(SystemRolePermission::getPermissionValue).toList();
        if(rolePermissions.contains(permission)){
            return true;
        }
        //第三步, 匹配用户所属角色的权限节点,
        if(matchPermission(rolePermissions, permission)){
            return true;
        }
        //最后匹配用户节点
        return matchPermission(values, permission);
    }

    /**
     * 匹配权限集合和权限节点
     *
     * <p>
     * 匹配方式:
     * <li> system.* 匹配所有权限
     * <li> system.user.* 匹配所有用户相关权限(增删改查等)
     * <li> system.role.* 匹配所有角色相关权限(增删该查等)
     *
     * @param values 权限集合
     * @param permission 权限节点
     * @return 匹配返回真, 否则返回假
     */
    public boolean matchPermission(List<String> values, String permission){
        String[] split = permission.split("\\.");
        start: for (String value : values) {
            String[] splitValue = value.split("\\.");
            if(splitValue.length == 0 || split.length == 0){
                throw new RuntimeException("权限节点分割错误, 如果你不知道发生了什么. 请联系维护人员");
            }
            int index = 0;
            while(index < splitValue.length && index < split.length){
                if(split[index].equals(splitValue[index])){
                    index++;
                }else if("*".equals(splitValue[index])){
                    return true;
                }else{
                    continue start;
                }
            }
        }
        return false;
    }
}
