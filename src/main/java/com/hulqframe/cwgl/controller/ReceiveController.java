package com.hulqframe.cwgl.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.model.Receive;
import com.hulqframe.cwgl.service.ReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cwgl/receive")
public class ReceiveController extends BaseController<Receive,Integer> {
    @Autowired
    private ReceiveService receiveService;
    @Override
    public BaseService<Receive, Integer> getService() {
    return receiveService;
    }



}
