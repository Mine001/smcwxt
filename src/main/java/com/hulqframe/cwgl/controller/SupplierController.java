package com.hulqframe.cwgl.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.cwgl.model.Supplier;
import com.hulqframe.cwgl.service.SupplierService;
import com.hulqframe.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;

@Controller
@RequestMapping("/cwgl/supplier")
public class SupplierController extends BaseController<Supplier,Integer> {
    @Autowired
    private SupplierService supplierService;
    @Override
    public BaseService<Supplier, Integer> getService() {
    return supplierService;
    }

    @RequestMapping("/listForSelect")
    public String listForSelect(@CurrentUser User user, ServletRequest request, Model model){
        setParameters(request,model);

        return getTemplatePath("listForSelect");
    }

}
