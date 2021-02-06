package com.hulqframe.generate.mapper;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.generate.model.DataTableField;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataTableFieldMapper extends BaseMapper<DataTableField,Integer> {
    @Delete("delete from g_table_field where table_id=#{tableId}")
    public void deleteByTableId(@Param("tableId") Integer tableId);

    @Select("select * from g_table_field where table_id=#{tableId}")
    List<DataTableField> listByTableId(Integer tableId);
}
