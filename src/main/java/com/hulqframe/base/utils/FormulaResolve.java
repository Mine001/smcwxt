package com.hulqframe.base.utils;
import com.github.pagehelper.PageInfo;
import com.hulqframe.base.asp.MybatisFormula;
import com.hulqframe.base.asp.MybatisJoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class FormulaResolve {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public  <T> void loadPropertyForPage(Class cls, PageInfo pageInfo)  {
        boolean hasLoadPropertyAnno = cls.isAnnotationPresent(MybatisJoin.class);
        if (hasLoadPropertyAnno) {
            //为属性赋值
            configFieldForPage(cls, pageInfo);
        }
    }

    private  <T> void configFieldForPage(Class<? extends Object> cls, PageInfo pageInfo) {
        try {
            if(pageInfo!=null&&pageInfo.getList()!=null){
                for(Object t:pageInfo.getList()){
                    Field[] fields = cls.getDeclaredFields();
                    for (Field field : fields) {
                        boolean hasConfigField = field.isAnnotationPresent(MybatisFormula.class);
                        if (hasConfigField) {  //若属性上有注解，使用注解的值作为key去配置文件中查找
                            String selectSql = field.getAnnotation(MybatisFormula.class).selectSql();
                            String fieldName = field.getAnnotation(MybatisFormula.class).field();
                            Field valueField= cls.getDeclaredField(fieldName);
                            valueField.setAccessible(true);
                            List params=new ArrayList();
                            params.add(valueField.get(t));
                            try {
                                Object annoValue = jdbcTemplate.queryForObject(selectSql,
                                        params.toArray(),java.lang.String.class);
                                field.setAccessible(true);
                                field.set(t, getNewValue(field,annoValue));
                            }catch (Exception e){
                                e.printStackTrace();
                                field.setAccessible(true);
                                field.set(t, "");
                            }
                        }

                    }
                }
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {

        }

    }
    public Object getNewValue(Field field,Object annoValue){
        Object newValue;
        if(annoValue==null){
            return annoValue;
        }
        if(field.getType()== BigDecimal.class){
            newValue=new BigDecimal(String.valueOf(annoValue));
        }else{
            newValue=String.valueOf(annoValue);
        }
        return newValue;
    }
    public  <T> void loadProperty(T t)  {
        if(t!=null){
            Class<? extends Object> cls = t.getClass();
            boolean hasLoadPropertyAnno = cls.isAnnotationPresent(MybatisJoin.class);
            if (hasLoadPropertyAnno) {
                //为属性赋值
                configField(cls, t);
            }
        }

    }

    private  <T> void configField(Class<? extends Object> cls, T t) {
        try {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                boolean hasConfigField = field.isAnnotationPresent(MybatisFormula.class);
                if (hasConfigField) {  //若属性上有注解，使用注解的值作为key去配置文件中查找
                    String selectSql = field.getAnnotation(MybatisFormula.class).selectSql();
                    String fieldName = field.getAnnotation(MybatisFormula.class).field();
                    Field valueField= cls.getDeclaredField(fieldName);
                    valueField.setAccessible(true);
                    List params=new ArrayList();
                    params.add(valueField.get(t));
                    try {
                        Object annoValue = jdbcTemplate.queryForObject(selectSql,
                                params.toArray(),java.lang.String.class);
                        field.setAccessible(true);
                        field.set(t, getNewValue(field,annoValue));
                    }catch (Exception e){
                        e.printStackTrace();
                        field.setAccessible(true);
                        field.set(t, "");
                    }
                }

            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {

        }

    }

    public  <T> void loadPropertyForList(Class cls, List list)  {
        boolean hasLoadPropertyAnno = cls.isAnnotationPresent(MybatisJoin.class);
        if (hasLoadPropertyAnno) {
            //为属性赋值
            configFieldForList(cls, list);
        }
    }

    private  <T> void configFieldForList(Class<? extends Object> cls, List list) {
        try {
            if(list!=null){
                for(Object t:list){
                    Field[] fields = cls.getDeclaredFields();
                    for (Field field : fields) {
                        boolean hasConfigField = field.isAnnotationPresent(MybatisFormula.class);
                        if (hasConfigField) {  //若属性上有注解，使用注解的值作为key去配置文件中查找
                            String selectSql = field.getAnnotation(MybatisFormula.class).selectSql();
                            String fieldName = field.getAnnotation(MybatisFormula.class).field();
                            Field valueField= cls.getDeclaredField(fieldName);
                            valueField.setAccessible(true);
                            List params=new ArrayList();
                            params.add(valueField.get(t));
                            try {
                              Object annoValue = jdbcTemplate.queryForObject(selectSql,
                                      params.toArray(),java.lang.String.class);
                              field.setAccessible(true);
                              field.set(t, getNewValue(field,annoValue));
                            }catch (Exception e){
                                e.printStackTrace();
                                field.setAccessible(true);
                                field.set(t, "");
                            }
                        }

                    }
                }
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {

        }

    }
}
