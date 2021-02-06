package com.hulqframe.cwgl.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.cwgl.mapper.ReceiptFormMapper;
import com.hulqframe.cwgl.model.ReceiptForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptFormService extends BaseService<ReceiptForm,Integer> {
@Autowired
private ReceiptFormMapper receiptFormMapper;
@Override
public BaseMapper<ReceiptForm, Integer> getBaseMapper() {
return receiptFormMapper;
}


}
