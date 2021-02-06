package  com.hulqframe.member.mapper;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.member.model.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMapper extends BaseMapper<Member,Integer> {
}
