package com.hulqframe.cwgl.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.mapper.OpenedInvoiceMapper;
import com.hulqframe.cwgl.model.OpenedInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenedInvoiceService extends BaseService<OpenedInvoice,Integer> {
@Autowired
private OpenedInvoiceMapper openedInvoiceMapper;
@Override
public BaseMapper<OpenedInvoice, Integer> getBaseMapper() {
return openedInvoiceMapper;
}
}
