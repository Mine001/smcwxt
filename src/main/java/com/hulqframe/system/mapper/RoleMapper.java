package com.hulqframe.system.mapper;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.model.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role,Integer> {
    @Insert("<script> insert into s_role_permission (role_id,permission_id) values  " +
            "  <foreach collection='permissionIds' item='item' separator=',' > " +
            "  (#{roleId},#{item})\n" +
            "  </foreach> </script>")
    boolean updateRolePermission(@Param("roleId") Integer roleId,@Param("permissionIds") Integer[] permissionIds);

    @Delete("delete from s_role_permission where role_id=#{roleId}")
    boolean deleteRolePermission(@Param("roleId") Integer roleId);

    @Select("select * from s_role where id in(select role_id from s_user_role where user_id=#{userId})")
    List<Role> listByUserId(@Param("userId") Integer userId);
    @Select("select role_id from s_user_role where user_id=#{userId}")
    List<Integer> listRoleIdByUserId(@Param("userId")Integer userId);
}
