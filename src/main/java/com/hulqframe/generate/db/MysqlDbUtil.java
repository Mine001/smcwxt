package com.hulqframe.generate.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MysqlDbUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static final String PRI_KEY="id";
    public static final String CREATE_TABLE_START="CREATE TABLE ";
    public static final String DROP_TABLE_START="DROP TABLE ";
    public static final String ALTER_TABLE_START="ALTER TABLE ";
    /**
     * 建表
     * */
    public void createTable(String tableName,String comment){
        StringBuilder sb = new StringBuilder(CREATE_TABLE_START);
        sb.append(tableName).append("(`id` int(11) NOT NULL AUTO_INCREMENT,PRIMARY KEY (`id`) )COMMENT = '").
                append(comment).append("';");
        jdbcTemplate.execute(sb.toString());
    }
    /**
     * 删表
     * */
    public void dropTable(String tableName){
        StringBuilder sb = new StringBuilder(DROP_TABLE_START);
        sb.append(tableName).append(";");
        jdbcTemplate.execute(sb.toString());
    }
    /**
     * 表重命名
     * */
    public void renameTable(String tableName,String newName){
        StringBuilder sb = new StringBuilder(ALTER_TABLE_START);
        sb.append(tableName).append(" rename to ").append(newName).append(";");
        jdbcTemplate.execute(sb.toString());
    }
    /**
     * 修改表备注
     * */
    public void alterTableComment(String tableName,String comment){
        StringBuilder sb = new StringBuilder(ALTER_TABLE_START);
        sb.append(tableName).append(" comment '").append(comment).append("';");
        jdbcTemplate.execute(sb.toString());
    }

    /**
     * 添加字段
     * */
    public void addField(String tableName,String columnName,String columnType,String comment,
                         boolean isNotnull,String defaultValue){
        StringBuilder sb = new StringBuilder(ALTER_TABLE_START);
        sb.append(tableName).append(" add ").append(columnName).append(" ").
                append(columnType);
        if(isNotnull){
            sb.append("  null");
        }else{
            sb.append(" not null");
        }
        if(defaultValue!=null&&!defaultValue.equals("")){
            sb.append(" DEFAULT '").append(defaultValue).append("'");
        }
        sb.append("  comment '").append(comment).append("';");
        jdbcTemplate.execute(sb.toString());
    }
    /**
     * 修改字段名称
     * */
    public void alterFieldName(String tableName,String oldName,String newName,String columnType,String comment,
                           boolean isNotnull,String defaultValue){
        StringBuilder sb = new StringBuilder(ALTER_TABLE_START);
        sb.append(tableName).append(" CHANGE  COLUMN  ").append(oldName).append(" ").append(newName).append(" ").
                append(columnType);
        if(isNotnull){
            sb.append(" null");
        }else{
            sb.append(" not null");
        }
        if(defaultValue!=null&&!defaultValue.equals("")){
            if(columnType.startsWith("BIT")){
                sb.append(" DEFAULT ").append(defaultValue);
            }else{
                sb.append(" DEFAULT '").append(defaultValue).append("'");
            }

        }
        sb.append("  comment '").append(comment).append("';");
        jdbcTemplate.execute(sb.toString());
    }
    /**
     * 修改字段
     * */
    public void alterField(String tableName,String columnName,String columnType,String comment,
                         boolean isNotnull,String defaultValue){
        StringBuilder sb = new StringBuilder(ALTER_TABLE_START);
        sb.append(tableName).append(" modify ").append(columnName).append(" ").
                append(columnType);
        if(isNotnull){
            sb.append(" null");
        }else{
            sb.append(" not null");
        }
        if(defaultValue!=null&&!defaultValue.equals("")){
            if(columnType.startsWith("BIT")){
                sb.append(" DEFAULT ").append(defaultValue);
            }else{
                sb.append(" DEFAULT '").append(defaultValue).append("'");
            }

        }
        sb.append("  comment '").append(comment).append("';");
        jdbcTemplate.execute(sb.toString());
    }
    /**
     * 删除字段
     * */
    public void delField(String tableName,String columnName){
        StringBuilder sb = new StringBuilder(ALTER_TABLE_START);
        sb.append(tableName).append(" drop ").append(columnName).append(";");
        jdbcTemplate.execute(sb.toString());
    }
}
