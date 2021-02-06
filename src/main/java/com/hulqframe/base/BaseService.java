package com.hulqframe.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.base.utils.FormulaResolve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;
@Service
public abstract class BaseService<T,PK extends Serializable> {
    @Autowired
    protected FormulaResolve formulaResolve;
    public  Class<T> entityClass;
    public abstract BaseMapper<T,PK> getBaseMapper();
    /**
     * 保存方法
     * */
    public T save(T entity){
        getBaseMapper().insert(entity);
        return entity;
    }

    /**
     * 修改防范，只修改不为null的数据
     * */
    public void update(T entity){
        getBaseMapper().updateByPrimaryKeySelective(entity);
    }

    /**
     * 删除方法
     * */
    public void delete(PK id){
        getBaseMapper().deleteByPrimaryKey(id);
    }

    /**
     * 获得全部列表
     * */
    public List<T> listAll(){
        List list=getBaseMapper().selectAll();
        formulaResolve.loadPropertyForList(this.entityClass,list);
        return list;
    }
    /**
     * 根据条件查询列表
     * */
    public List<T> listByParams(List<SearchBean> searchBeans,String orderBy){
        List list= getBaseMapper().selectByExample(getExample(this.entityClass,searchBeans,orderBy));
        formulaResolve.loadPropertyForList(this.entityClass,list);
        return list;
    }
    /**
     * 根据条件查询分页
     * */
    public PageInfo<T>  page(PageInfo<T>  page,List<SearchBean> searchBeans,String orderBy){
        if(page.getPageSize()>0){
            PageHelper.startPage(page.getPageNum(),page.getPageSize());
        }
        List<T> list=listByParams(searchBeans,orderBy);
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        formulaResolve.loadPropertyForPage(this.entityClass,pageInfo);
        return pageInfo;

    }

    /**
     * 根据ID获取对象
     * */
    public T findById(PK id)
    {
        T t=getBaseMapper().selectByPrimaryKey(id);
        formulaResolve.loadProperty(t);
        return t;
    }
    /**
     * 根据唯一标识获取对象
     * */
    public T findByUnique(String property,Object value){
        Example example=new Example(this.entityClass);
        example.and(example.createCriteria().andEqualTo(property,value));
        return  getBaseMapper().selectOneByExample(example);
    }
    /**
     * 根据唯一标识获取对象
     * */
    public int getCountByParams(List<SearchBean> searchBeans){
        return  getBaseMapper().selectCountByExample(getExample(this.entityClass,searchBeans,null));
    }
    /**
     * 根据条件组合获取对象
     * */
    public T findObjectByParams(List<SearchBean> searchBeans){
        return  getBaseMapper().selectOneByExample(getExample(this.entityClass,searchBeans,null));
    }
    /**
     * 获取实体类型名称
     * 子类可覆盖此方法，返回：泛型T的类名.class.getSimpleName()
     * @return
     */
    public BaseService()  {
        try{
            Class clazz = this.getClass();
            ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
            this.entityClass = (Class<T>) pt.getActualTypeArguments()[0];
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static Example getExample(Class clazz, List<SearchBean> searchBeans, String orderBy){
        Example example=new Example(clazz);
        Example.Criteria criteria=example.createCriteria();
        if(orderBy!=null&&!orderBy.equals("")){
            String val[]=orderBy.split("_");
            if(val.length<2||val[1].toUpperCase().trim().equals("AES")){
                example.orderBy(val[0].trim()).asc();
            }else{
                example.orderBy(val[0].trim()).desc();
            }

        }
        if(searchBeans==null||searchBeans.size()==0){
            return example;
        }
        for(SearchBean searchBean:searchBeans){
            if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.LIKE.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andLike(searchBean.getField(),SearchBean.SEARCH_CRITERIA_PERCENT+String.valueOf(searchBean.getValue())+SearchBean.SEARCH_CRITERIA_PERCENT);
                }else{
                    criteria.orLike(searchBean.getField(),SearchBean.SEARCH_CRITERIA_PERCENT+String.valueOf(searchBean.getValue())+SearchBean.SEARCH_CRITERIA_PERCENT);
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.STARTWITH.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andLike(searchBean.getField(),String.valueOf(searchBean.getValue())+SearchBean.SEARCH_CRITERIA_PERCENT);
                }else{
                    criteria.orLike(searchBean.getField(),String.valueOf(searchBean.getValue())+SearchBean.SEARCH_CRITERIA_PERCENT);
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.ENDWITH.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andLike(searchBean.getField(),SearchBean.SEARCH_CRITERIA_PERCENT+String.valueOf(searchBean.getValue()));
                }else{
                    criteria.orLike(searchBean.getField(),SearchBean.SEARCH_CRITERIA_PERCENT+String.valueOf(searchBean.getValue()));
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.IN.getKey())){
                String values[]=String.valueOf(searchBean.getValue()).split(",");
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andIn(searchBean.getField(), Arrays.asList(values));
                }else{
                    criteria.orIn(searchBean.getField(), Arrays.asList(values));
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.EQ.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andEqualTo(searchBean.getField(), searchBean.getValue());
                }else{
                    criteria.orEqualTo(searchBean.getField(), searchBean.getValue());
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.GT.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andGreaterThan(searchBean.getField(), searchBean.getValue());
                }else{
                    criteria.orGreaterThan(searchBean.getField(), searchBean.getValue());
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.GTE.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andGreaterThanOrEqualTo(searchBean.getField(), searchBean.getValue());
                }else{
                    criteria.orGreaterThanOrEqualTo(searchBean.getField(), searchBean.getValue());
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.LT.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andLessThan(searchBean.getField(), searchBean.getValue());
                }else{
                    criteria.orLessThan(searchBean.getField(), searchBean.getValue());
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.LTE.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andLessThanOrEqualTo(searchBean.getField(), searchBean.getValue());
                }else{
                    criteria.orLessThanOrEqualTo(searchBean.getField(), searchBean.getValue());
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.NE.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andNotEqualTo(searchBean.getField(), searchBean.getValue());
                }else{
                    criteria.orNotEqualTo(searchBean.getField(), searchBean.getValue());
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.NOTIN.getKey())){
                String values[]=String.valueOf(searchBean.getValue()).split(",");
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andNotIn(searchBean.getField(), Arrays.asList(values));
                }else{
                    criteria.orNotIn(searchBean.getField(), Arrays.asList(values));
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.NOTNULL.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andIsNotNull(searchBean.getField());
                }else{
                    criteria.orIsNotNull(searchBean.getField());
                }
            } if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.NOTLIKE.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andNotLike(searchBean.getField(),SearchBean.SEARCH_CRITERIA_PERCENT+String.valueOf(searchBean.getValue())+SearchBean.SEARCH_CRITERIA_PERCENT);
                }else{
                    criteria.orNotLike(searchBean.getField(),SearchBean.SEARCH_CRITERIA_PERCENT+String.valueOf(searchBean.getValue())+SearchBean.SEARCH_CRITERIA_PERCENT);
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.NOTSTARTWITH.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andNotLike(searchBean.getField(),String.valueOf(searchBean.getValue())+SearchBean.SEARCH_CRITERIA_PERCENT);
                }else{
                    criteria.orNotLike(searchBean.getField(),String.valueOf(searchBean.getValue())+SearchBean.SEARCH_CRITERIA_PERCENT);
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.NOTENDWITH.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andNotLike(searchBean.getField(),SearchBean.SEARCH_CRITERIA_PERCENT+String.valueOf(searchBean.getValue()));
                }else{
                    criteria.orNotLike(searchBean.getField(),SearchBean.SEARCH_CRITERIA_PERCENT+String.valueOf(searchBean.getValue()));
                }
            }else if(searchBean.getMatchType().equals(SearchBean.SEARCH_CRITERIA_MATCHTYPE.ISNULL.getKey())){
                if(searchBean.getAndOr().equals(SearchBean.AND_STR)){
                    criteria.andIsNull(searchBean.getField());
                }else{
                    criteria.orIsNull(searchBean.getField());
                }
            }

        }
        return example;
    }



}
