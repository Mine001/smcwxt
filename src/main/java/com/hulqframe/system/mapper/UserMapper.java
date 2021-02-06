package com.hulqframe.system.mapper;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.system.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper  extends BaseMapper<User,Integer> {
    @Select("select * from s_user where account=#{account}")
    User findByAccount(@Param("account") String account);
    @Insert("<script> insert into s_user_role (user_id,role_id) values  " +
            "  <foreach collection='roleIds' item='item' separator=',' > " +
            "  (#{id},#{item})\n" +
            "  </foreach> </script>")
    boolean updateUserRole(@Param("id")Integer id, @Param("roleIds")Integer[] roleIds);
    @Delete("delete from s_user_role where user_id=#{id}")
    boolean deleteUserRole(@Param("id") Integer id);
}
