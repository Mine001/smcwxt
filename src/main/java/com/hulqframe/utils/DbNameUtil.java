package com.hulqframe.utils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据库驼峰命名
 * */
public class DbNameUtil {
    /**
     * 驼峰命名转为带下划线的列名
     *
     */
    public static String parseToColumn(String fieldName) {
        StringBuilder columnName = new StringBuilder();
        for (int i = 0; i < fieldName.length(); i++) {
            char c = fieldName.charAt(i);
            // 如果是大写字母就加上下划线，并将其变为小写
            if (Character.isUpperCase(c)) {
                columnName.append("_").append(c += 32);
            } else {
                columnName.append(c);
            }
        }
        return columnName.toString();

    }

    /**
     * 驼峰命名转为带下划线的列名
     *
     */
    public static String parseToField(String columnName) {
        StringBuilder fieldName = new StringBuilder();
        String[] names=columnName.split("_");
        int i=0;
        for (String name: names) {
            if(i==0){
                fieldName.append(name);
            }else{
                fieldName.append(upperCase1th(name));
            }
            i++;
        }
        return fieldName.toString();

    }
    /**
     * 驼峰命名转为带下划线的列名
     *
     */
    public static String parseToClassName(String tableName) {
        StringBuilder className = new StringBuilder();
        String[] names=tableName.split("_");
        for (String name: names) {
            if(name.length()==1){
                continue;
            }
            className.append(upperCase1th(name));
        }
        return className.toString();

    }
    /**
     * 首字母大写
     * */
    public static String upperCase1th(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    /**
     * 首字母小写
     * */
    public static String lowerCase1th(String str){
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }


    /**
     * 获取java类型对应的jdbcType
     * */
    private String getJdbcType(Class type) {
        if(type.isInstance(String.class)){
            return "VARCHAR";
        }else if(type.isInstance(Date.class)){
            return "TIMESTAMP";
        }else if(type.isInstance(int.class)){
            return "INTEGER";
        }else if(type.isInstance(boolean.class)||type.isInstance(Boolean.class)){
            return "BOOLEAN";
        }else if(type.isInstance(long.class)||type.isInstance(Long.class)){
            return "BIGINT";
        }else if(type.isInstance(byte.class)||type.isInstance(Byte.class)){
            return "TINYINT";
        }else if(type.isInstance(short.class)||type.isInstance(Short.class)){
            return "SMALLINT";
        }else if(type.isInstance(double.class)||type.isInstance(Double.class)){
            return "DOUBLE";
        }else if(type.isInstance(BigDecimal.class)){
            return "DECIMAL";
        }
        return "VARCHAR";
    }
    /**
     * 获取java类型对应的jdbcType
     * */
    public static String getJaveType(String type) {
        if(type.equals("INT")||type.equals("INTEGER")){
            return "Integer";
        }else if(type.equals("VARCHAR")||type.equals("CHAR")
        ||type.equals("BLOB")||type.equals("TEXT")||type.equals("LONGTEXT")){
            return "String";
        }else if(type.equals("BIGINT")){
            return "Long";
        }else if(type.equals("FLOAT")){
            return "Float";
        }else if(type.equals("DOUBLE")){
            return "Double";
        }else if(type.equals("BIT")){
            return "Boolean";
        }else if(type.equals("DECIMAL")){
            return "BigDecimal";
        }else if(type.equals("DATE")||type.equals("DATETIME")||type.equals("TIMESTAMP")){
            return "Date";
        }
        return "String";
    }
}
