package com.hulqframe.base.asp;

import java.lang.annotation.*;
/**
 * mybatis注解，用户查询属性值
 * */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MybatisFormula {
     String selectSql();

     String field();
}
