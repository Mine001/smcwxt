package com.hulqframe.base.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static String schema;
    @Value("${database.schema}")
    public void setSchema(String schema) {
        Constants.schema = schema;
    }

    public static String getSchema() {
        return schema;
    }

    public static final String LOGIN_MEMBER="LOGIN_MEMBER";
    /**
     * 菜单类型
     * */
    public enum PERMISSION_TYPE{
        CATALOG("0","目录"),
        MENU("1","菜单"),
        ACTION("2","操作"),
        PERM("3","权限");
        @Setter
        @Getter
        private String value;
        @Setter
        @Getter
        private String text;

        PERMISSION_TYPE(String value, String text) {
            this.value = value;
            this.text = text;
        }
    }
    /**
     * 会员菜单类型  会员不控制按钮
     * */
    public enum MEMBER_PERMISSION_TYPE{
        CATALOG("0","目录"),
        MENU("1","菜单"),
        PERM("2","权限");
        @Setter
        @Getter
        private String value;
        @Setter
        @Getter
        private String text;

        MEMBER_PERMISSION_TYPE(String value, String text) {
            this.value = value;
            this.text = text;
        }
    }
    /**
     * 字段类型
     * */
    public enum MODEL_FIELD_CLASS{
        INPUT("input","单行文本"),
        FILE("file","文件"),
        IMAGE("image","图片"),
        EDITOR("editor","编辑器"),
        TEXTAREA("textarea","多文本框"),
        RADIO("radio","单选"),
        SELECT("select","下拉列表"),
        CHECKBOX("checkbox","多选"),
        DATEINPUT("dateInput","日期");

        MODEL_FIELD_CLASS(String value, String text) {
            this.value = value;
            this.text = text;
        }
        @Setter
        @Getter
        private String value;
        @Setter
        @Getter
        private String text;

    }
    /**
     *
     * */
    public enum CODE_TYPE{
        MODEL("1","","生成对象模型","model","java","model.tpl"),
        CONTROLLER("2","Controller","生成controller类","controller","java","controller.tpl"),
        SERVICE("3","Service","生成service类","service","java","service.tpl"),
        MAPPER("4","Mapper","生成mapper类","mapper","java","mapper.tpl"),
        INDEX("5","index","生成index页面","","html","index.tpl"),
        EDIT("6","edit","生成edit页面","","html","edit.tpl"),
        LIST("7","list","list页面","","html","list.tpl"),
        DETAIL("8","detail","生成detail页面","","html","detail.tpl"),
        API("9","ApiController","生成apiController类","","java","apiController.tpl"),;

        CODE_TYPE(String value,String splicing, String text,String packageName,String type,String tpl) {
            this.splicing = splicing;
            this.text = text;
            this.packageName=packageName;
            this.type=type;
            this.value=value;
            this.tpl=tpl;
        }
        @Setter
        @Getter
        private String value;//值
        @Setter
        @Getter
        private String splicing;//拼接
        @Setter
        @Getter
        private String type;//类型 java或者html
        @Setter
        @Getter
        private String packageName;//包名
        @Setter
        @Getter
        private String text;//描述
        @Setter
        @Getter
        private String tpl;//模板

        public static CODE_TYPE getByValue(String value){
            for(CODE_TYPE code_type:CODE_TYPE.values()){
                if(code_type.getValue().equals(value)){
                    return code_type;
                }
            }
            return null;
        }

    }
    /**
     * 按钮类型
     * */
    public enum ACTION_TYPE{
        ADD("/edit","新增","add"),
        EDIT("/edit","修改","edit"),
        DETAIL("/detail","查看","detail"),
        DEL("/delete","删除","del");

        ACTION_TYPE(String path, String name, String action) {
            this.path = path;
            this.name = name;
            this.action = action;
        }

        @Setter
        @Getter
        private String path;
        @Setter
        @Getter
        private String name;
        @Setter
        @Getter
        private String action;

    }
    public static final String FILED_TYPES[]={"INT","VARCHAR","INTEGER","BIGINT","FLOAT","DOUBLE","DECIMAL","DATE",
    "DATETIME","TIMESTAMP","CHAR","BLOB","TEXT","LONGTEXT","BIT"};



}
