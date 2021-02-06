package com.hulqframe.generate.model;

import com.hulqframe.utils.DbNameUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "g_table_field_condition")
public class TableFieldCondition implements Serializable {

        /**
        * 主键
        */
        @Id
        @Column(name = "id")
        @Setter @Getter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        /**
        * 匹配类型
        */
        @Column(name = "matchType")
        @Setter @Getter
        private String matchType;

        /**
        * 逻辑关系
        */
        @Column(name = "andOr")
        @Setter @Getter
        private String andOr;

        /**
        * 对应表格ID
        */
        @Column(name = "table_id")
        @Setter @Getter
        private Integer tableId;

        /**
        * 字段名称
        */
        @Column(name = "field_name")
        @Setter @Getter
        private String fieldName;

        /**
        * 显示名称
        */
        @Column(name = "name")
        @Setter @Getter
        private String name;

        public TableFieldCondition(String matchType, String andOr, Integer tableId, String fieldName, String name) {
                this.matchType = matchType;
                this.andOr = andOr;
                this.tableId = tableId;
                this.fieldName = fieldName;
                this.name = name;
        }

        //根据驼峰命名规则获取java对象属性
        public String getJavaField(){
                return DbNameUtil.parseToField(this.getFieldName());
        }
}