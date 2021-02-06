package com.hulqframe.system.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.system.mapper.PermissionMapper;
import com.hulqframe.system.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = "PERMISSION_CACHE")
@Service
public class PermissionService extends BaseService<Permission,Integer> {
    @Autowired
    private PermissionMapper permissionMapper;

    public  List<Permission> listByType(String value){
        List<SearchBean> searchBeans=new ArrayList<SearchBean>();
        SearchBean searchBean=new SearchBean(value,"SEARCH_AND_type_EQ");
        searchBeans.add(searchBean);
        SearchBean searchBean2=new SearchBean("1","SEARCH_AND_id_NE");
        searchBeans.add(searchBean2);
        return listByParams(searchBeans," seq ");
    }

    @Override
    @CacheEvict(value ="PERMISSION_CACHE",allEntries = true)
    public void update(Permission entity) {
        super.update(entity);
    }

    @Override
    @CacheEvict(value ="PERMISSION_CACHE",allEntries = true)
    public void delete(Integer id) {
        super.delete(id);
    }

    @Cacheable(key = "'list-by-pid-'+#p0")
    public List<Permission> listByPid(Integer parentId) {
        List<SearchBean> searchBeans=new ArrayList<SearchBean>();
        SearchBean searchBean=new SearchBean(String.valueOf(parentId),"SEARCH_AND_parentId_EQ");
        searchBeans.add(searchBean);
        return listByParams(searchBeans," seq ");
    }
    /**
     * 通过角色ID获取权限列表
     * */
//    @Cacheable(key = "'list-by-role-id-'+#p0")
    public List<Permission> listByRoleId(Integer roleId){
        return permissionMapper.listByRoleId(roleId);
    }
    /**
     * 通过用户ID获取权限列表
     * */
    public  List<Permission> listByUserId(Integer userId){
        return permissionMapper.listByUserId(userId);
    }

    public List<Permission> listByUserAction(int pId, Integer userId) {
        return permissionMapper.listByUserAction(pId,userId);
    }
    @Override
    public BaseMapper<Permission, Integer> getBaseMapper() {
        return permissionMapper;
    }

}
