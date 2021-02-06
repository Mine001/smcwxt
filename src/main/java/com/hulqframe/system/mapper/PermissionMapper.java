package com.hulqframe.system.mapper;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.system.model.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper extends BaseMapper<Permission,Integer> {
    @Select("select * from s_permission where id in(select permission_id from s_role_permission where role_id=#{roleId})")
    List<Permission> listByRoleId(@Param("roleId") Integer roleId);
    @Select("select * from s_permission where id in(select permission_id from s_role_permission where role_id in " +
            "(select role_id from s_user_role where user_id =#{userId}))")
    List<Permission> listByUserId(@Param("userId") Integer userId);
    @Select("select * from s_permission where id in(select permission_id from s_role_permission where role_id in " +
            "(select role_id from s_user_role where user_id =#{userId}))" +
            " and parent_id=#{pId} and type=2")
    List<Permission> listByUserAction(@Param("pId") Integer pId, @Param("userId") Integer userId);
}
