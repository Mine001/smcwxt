package  com.hulqframe.member.mapper;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.member.model.MemberPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberPermissionMapper extends BaseMapper<MemberPermission,Integer> {
    @Select("select * from m_member_permission m where  is_delete=false and m.id in(select permission_id from m_role_permission where role_id =#{roleId})")
    List<MemberPermission> listByRoleId(@Param("roleId") Integer roleId);
    @Select("select * from m_member_permission m where is_delete=false and m.id in(select permission_id from m_role_permission where role_id  in (select role_id from m_member_role_config where member_id=#{userId}))")
    List<MemberPermission> listByUserId(@Param("userId") Integer userId);
}
