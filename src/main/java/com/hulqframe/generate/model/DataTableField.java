package com.hulqframe.generate.model;

import com.alibaba.fastjson.JSONArray;
import com.hulqframe.base.common.Constants;
import com.hulqframe.utils.DbNameUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "g_table_field")
public class DataTableField implements Serializable {
    /**
     * 字段编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;

    /**
     * 模型编号
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
     * 字段类型（如文本）
     */
    @Column(name = "field_class")
    @Setter @Getter
    private String fieldClass;

    /**
     * 字段类别（数据库类别）
     */
    @Column(name = "field_type")
    @Setter @Getter
    private String fieldType;

    /**
     * 中文别名
     */
    @Setter @Getter
    @Column(name = "name")
    private String name;

    /**
     * 是否为空
     */
    @Column(name = "not_null")
    @Setter @Getter
    private Boolean notNull;

    /**
     * 字段长度
     */
    @Column(name = "field_length")
    @Setter @Getter
    private Integer fieldLength;

    /**
     * 是否为主键
     */
    @Column(name = "is_primary")
    @Setter @Getter
    private Boolean isPrimary;

    /**
     * 字段设置
     */
    @Column(name = "setting")
    @Setter @Getter
    private String setting;

    /**
     * 字段值
     */
    @Column(name = "field_value")
    @Setter @Getter
    private String fieldValue;
    /**
     * 是否列表显示
     */
    @Column(name = "is_display")
    @Setter @Getter
    private Boolean isDisplay;
    /**
     * 是否隐藏域
     */
    @Column(name = "is_hidden")
    @Setter @Getter
    private Boolean isHidden;

    public String getFieldClassText(){
        for(Constants.MODEL_FIELD_CLASS model_field_class:Constants.MODEL_FIELD_CLASS.values()){
            if(model_field_class.getValue().equals(this.getFieldClass())){
                return model_field_class.getText();
            }
        }
        return "";
    }
    public JSONArray getSettingJson(){
        if(this.getSetting()!=null&&!this.getSetting().trim().equals("")){
            return JSONArray.parseArray(this.getSetting());
        }
        return new JSONArray();
    }
    //根据驼峰命名规则获取java对象属性
    public String getJavaField(){
        return DbNameUtil.parseToField(this.getFieldName());
    }
    //根据数据库类型获取对应的java类型
    public String getJavaType(){
        return DbNameUtil.getJaveType(this.getFieldType());
    }
}