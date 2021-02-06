package com.hulqframe.cwgl.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.mapper.PaymentMapper;
import com.hulqframe.cwgl.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService extends BaseService<Payment,Integer> {
@Autowired
private PaymentMapper paymentMapper;
@Override
public BaseMapper<Payment, Integer> getBaseMapper() {
return paymentMapper;
}
}
