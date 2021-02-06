package com.hulqframe.cwgl.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.mapper.SupplierMapper;
import com.hulqframe.cwgl.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService extends BaseService<Supplier,Integer> {
@Autowired
private SupplierMapper supplierMapper;
@Override
public BaseMapper<Supplier, Integer> getBaseMapper() {
return supplierMapper;
}
}
