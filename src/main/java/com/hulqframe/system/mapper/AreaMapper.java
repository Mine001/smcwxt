package  com.hulqframe.system.mapper;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.system.model.Area;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AreaMapper extends BaseMapper<Area,Integer> {
    @Select("select id,parent_id as pid,name as title from s_area where is_delete!='1'")
    List<Map<String, Object>> getInitAreaData();
}
