package com.hulqframe.system.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.system.mapper.RoleMapper;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService extends BaseService<Role,Integer> {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public BaseMapper<Role, Integer> getBaseMapper() {
        return roleMapper;
    }

    public  boolean updateRolePermission(Integer roleId, Integer[] permissionIds){
        roleMapper.deleteRolePermission(roleId);
        return roleMapper.updateRolePermission(roleId,permissionIds);
    }

    /**
     * 通过用户ID获取角色id集合
     * */
    public  List<Integer> listRoleIdByUserId(Integer userId){
        return roleMapper.listRoleIdByUserId(userId);
    }
    /**
     * 通过用户ID获取角色集合
     * */
    public  List<Role> listByUserId(Integer userId){
        return roleMapper.listByUserId(userId);

    }

}
