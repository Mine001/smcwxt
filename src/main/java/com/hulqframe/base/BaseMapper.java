package com.hulqframe.base;


import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;


//mapper基类
public interface BaseMapper<T,PK extends Serializable>  extends Mapper<T> {

}
