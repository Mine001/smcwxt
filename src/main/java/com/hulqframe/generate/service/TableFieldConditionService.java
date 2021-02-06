package com.hulqframe.generate.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.generate.mapper.TableFieldConditionMapper;
import com.hulqframe.generate.model.TableFieldCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableFieldConditionService extends BaseService<TableFieldCondition,Integer> {
@Autowired
private TableFieldConditionMapper tableFieldConditionMapper;
@Override
public BaseMapper<TableFieldCondition, Integer> getBaseMapper() {
return tableFieldConditionMapper;
}
}
