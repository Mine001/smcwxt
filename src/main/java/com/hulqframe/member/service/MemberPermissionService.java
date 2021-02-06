package com.hulqframe.member.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.member.mapper.MemberPermissionMapper;
import com.hulqframe.member.model.MemberPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Cacheable(value = "MEMBER_PERMISSION_CACHE")
public class MemberPermissionService extends BaseService<MemberPermission,Integer> {
@Autowired
private MemberPermissionMapper memberPermissionMapper;
@Override
public BaseMapper<MemberPermission, Integer> getBaseMapper() {
return memberPermissionMapper;
}

    @Cacheable(key = "'list-by-role-id-'+#p0")
    public List<MemberPermission> listByRoleId(Integer roleId) {
        return memberPermissionMapper.listByRoleId(roleId);
    }

    @Override
    @CacheEvict(value = "MEMBER_PERMISSION_CACHE",allEntries = true)
    public void update(MemberPermission entity) {
        super.update(entity);
    }

    @Override
    @CacheEvict(value = "MEMBER_PERMISSION_CACHE",allEntries = true)
    public void delete(Integer id) {
        super.delete(id);
    }

    public List<MemberPermission> listByUserId(Integer userId) {
        return memberPermissionMapper.listByUserId(userId);
    }
}
