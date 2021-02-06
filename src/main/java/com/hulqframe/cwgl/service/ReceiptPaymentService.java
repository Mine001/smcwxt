package com.hulqframe.cwgl.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.mapper.ReceiptPaymentMapper;
import com.hulqframe.cwgl.model.ReceiptPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptPaymentService extends BaseService<ReceiptPayment,Integer> {
@Autowired
private ReceiptPaymentMapper receiptPaymentMapper;
@Override
public BaseMapper<ReceiptPayment, Integer> getBaseMapper() {
return receiptPaymentMapper;
}

    public void deleteByPaymentId(Integer paymentId) {
        receiptPaymentMapper.deleteByPaymentId(paymentId);
    }
}
