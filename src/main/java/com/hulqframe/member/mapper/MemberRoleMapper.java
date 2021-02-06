package  com.hulqframe.member.mapper;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.member.model.MemberRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleMapper extends BaseMapper<MemberRole,Integer> {
    @Insert("<script> insert into m_role_permission (role_id,permission_id) values  " +
            "  <foreach collection='permissionIds' item='item' separator=',' > " +
            "  (#{roleId},#{item})\n" +
            "  </foreach> </script>")
    boolean updateRolePermission(@Param("roleId") Integer roleId, @Param("permissionIds") Integer[] permissionIds);

    @Delete("delete from m_role_permission where role_id=#{roleId}")
    boolean deleteRolePermission(@Param("roleId") Integer roleId);
}
