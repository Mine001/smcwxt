package com.hulqframe.member.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.member.mapper.MemberRoleMapper;
import com.hulqframe.member.model.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class MemberRoleService extends BaseService<MemberRole,Integer> {
@Autowired
private MemberRoleMapper memberRoleMapper;
@Override
public BaseMapper<MemberRole, Integer> getBaseMapper() {
return memberRoleMapper;
}

    @Caching(evict={ @CacheEvict(value = "MEMBER_PERMISSION_CACHE",key = " 'list-by-role-id-'+#p0") })
    public boolean updateRolePermission(Integer roleId, Integer[] permissionIds) {
        memberRoleMapper.deleteRolePermission(roleId);
        return memberRoleMapper.updateRolePermission(roleId,permissionIds);
    }
}
