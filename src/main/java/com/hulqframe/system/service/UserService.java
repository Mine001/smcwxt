package com.hulqframe.system.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.system.mapper.UserMapper;
import com.hulqframe.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "USER_CACHE")
public class UserService extends BaseService<User,Integer> {
    @Autowired
    private UserMapper userMapper;
    @Override
    public BaseMapper<User, Integer> getBaseMapper() {
        return userMapper;
    }
    public  User findByAccount(String account){
        return  userMapper.findByAccount(account);
    }
    public  boolean updateUserRole(Integer id, Integer[] roleIds){
        userMapper.deleteUserRole(id);
        return userMapper.updateUserRole(id,roleIds);
    }
}
