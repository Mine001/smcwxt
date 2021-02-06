package com.hulqframe.cwgl.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.model.OpenedInvoice;
import com.hulqframe.cwgl.service.OpenedInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cwgl/openedInvoice")
public class OpenedInvoiceController extends BaseController<OpenedInvoice,Integer> {
    @Autowired
    private OpenedInvoiceService openedInvoiceService;
    @Override
    public BaseService<OpenedInvoice, Integer> getService() {
    return openedInvoiceService;
    }



}
