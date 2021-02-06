package com.hulqframe.cwgl.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.mapper.ReceivedInvoiceMapper;
import com.hulqframe.cwgl.model.ReceivedInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceivedInvoiceService extends BaseService<ReceivedInvoice,Integer> {
@Autowired
private ReceivedInvoiceMapper receivedInvoiceMapper;
@Override
public BaseMapper<ReceivedInvoice, Integer> getBaseMapper() {
return receivedInvoiceMapper;
}
}
