package com.hulqframe.base.common;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;


import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 搜索条件
 * */
public class SearchBean {

    public static final String SEARCH_CRITERIA="SEARCH_";
    public static final String SEARCH_CRITERIA_SPLIT="_";
    public static final String SEARCH_CRITERIA_QUOTATION="'";
    public static final String SEARCH_CRITERIA_PERCENT="%";
    public static final String SEARCH_VALUE_SPLIT=",";
    public static final String SEARCH_BETWEEN_VALUE_SPLIT="__";

    public static final String AND_STR="AND";
    public static final String OR_STR="OR";

    @Setter @Getter
    private String field;//搜索字段
    @Setter @Getter
    private String matchType;//匹配类型
    @Setter @Getter
    private Object value;//条件值
    @Setter @Getter
    private String andOr;//与或

    public SearchBean(String field, String matchType, Object value, String andOr) {
        this.field = field;
        this.matchType = matchType;
        this.value = value;
        this.andOr = andOr;
    }
    /**
     * 根据搜索条件创建对象
     * */
    @SneakyThrows
    public SearchBean(Object vaule, String pname) {
        String a[]=pname.split(SEARCH_CRITERIA_SPLIT);
        this.andOr =a[1];
        this.field =a[2];
        this.matchType=a[3];
        this.matchType=this.matchType.toUpperCase();
        this.value=vaule;
    }


    public  enum  SEARCH_CRITERIA_MATCHTYPE{
        EQ("=","EQ","等于"),
        NE("!=","NE","不等于"),
        GT(">","GT","大于"),
        GTE(">=","GTE","大于等于"),
        LT("<","LT","小于"),
        LTE("<=","LTE","小于等于"),
        IN("in","IN","在集合里面"),
        NOTIN("not in","NOTIN","不在集合里面"),
        NOTNULL("not null","NOTNULL","不为空"),
        ISNULL("is null","ISNULL","为空"),
        LIKE("like","LIKE","包含"),
        STARTWITH("like","STARTWITH","以什么开始"),
        ENDWITH("like","ENDWITH","以什么结束"),
        NOTLIKE("not like","NOTLIKE","不包含"),
        NOTSTARTWITH("not like","NOTSTARTWITH","不以什么开始"),
        NOTENDWITH("not like","NOTENDWITH","不以什么结束");
        @Setter @Getter
        private String value;
        @Setter @Getter
        private String key;
        @Setter @Getter
        private String desc;

        SEARCH_CRITERIA_MATCHTYPE(java.lang.String value, java.lang.String key, java.lang.String desc) {
            this.key = key;
            this.value = value;
            this.desc = desc;
        }

        public static String getMatchType(String key){
            for(SEARCH_CRITERIA_MATCHTYPE search_criteria_matchtype:SEARCH_CRITERIA_MATCHTYPE.values()){
                if(search_criteria_matchtype.getKey().equals(key)){
                    return search_criteria_matchtype.getValue();
                }
            }
            return "";
        }

    }

}
