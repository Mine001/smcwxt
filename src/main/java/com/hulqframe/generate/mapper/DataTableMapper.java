package com.hulqframe.generate.mapper;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.common.Constants;
import com.hulqframe.generate.model.DataTable;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DataTableMapper extends BaseMapper<DataTable,Integer> {

    @Select("select table_comment as name,table_name as tableName,table_comment as des from information_schema.tables where " +
            "table_schema=#{schema} and table_name not in(select g.table_name from g_table g where is_delete!='1')")
    List<Map> getAllTable(@Param("schema") String schema);

    @Select("select column_comment as name,COLUMN_name as fieldName,data_type as fieldType,column_type as column_type,column_key as column_key" +
            ",COLUMN_DEFAULT as fieldValue,is_nullable as is_nullable " +
            "from information_schema.columns where table_schema = #{schema}  and table_name = #{tableName}" +
            "order by ORDINAL_POSITION asc")
    List<Map> getColumnByTableName(@Param("tableName")String tableName, @Param("schema")String schema);
}
