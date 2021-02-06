package com.hulqframe.cwgl.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.model.ProjectPayment;
import com.hulqframe.cwgl.service.ProjectPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cwgl/projectPayment")
public class ProjectPaymentController extends BaseController<ProjectPayment,Integer> {
    @Autowired
    private ProjectPaymentService projectPaymentService;
    @Override
    public BaseService<ProjectPayment, Integer> getService() {
    return projectPaymentService;
    }



}
