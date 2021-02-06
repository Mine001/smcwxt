package com.hulqframe.system.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.system.mapper.DataDictMapper;
import com.hulqframe.system.model.DataDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataDictService extends BaseService<DataDict,Integer> {
@Autowired
private DataDictMapper dataDictMapper;
@Override
public BaseMapper<DataDict, Integer> getBaseMapper() {
return dataDictMapper;
}
}
