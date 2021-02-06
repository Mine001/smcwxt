package com.hulqframe.cwgl.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.model.ReceiptPayment;
import com.hulqframe.cwgl.service.ReceiptPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cwgl/receiptPayment")
public class ReceiptPaymentController extends BaseController<ReceiptPayment,Integer> {
    @Autowired
    private ReceiptPaymentService receiptPaymentService;
    @Override
    public BaseService<ReceiptPayment, Integer> getService() {
    return receiptPaymentService;
    }



}
