package com.hulqframe.cwgl.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.model.ReceivedInvoice;
import com.hulqframe.cwgl.service.ReceivedInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cwgl/receivedInvoice")
public class ReceivedInvoiceController extends BaseController<ReceivedInvoice,Integer> {
    @Autowired
    private ReceivedInvoiceService receivedInvoiceService;
    @Override
    public BaseService<ReceivedInvoice, Integer> getService() {
    return receivedInvoiceService;
    }



}
