package com.hulqframe.system.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.system.model.AboutUs;
import com.hulqframe.system.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/system/aboutUs")
public class AboutUsController extends BaseController<AboutUs,Integer> {
    @Autowired
    private AboutUsService aboutUsService;
    @Override
    public BaseService<AboutUs, Integer> getService() {
    return aboutUsService;
    }

    @RequestMapping("/editNew")
    public String editNew(Model model, @RequestParam(required = false) Integer id,@RequestParam(required = false) Integer type){
        model.addAttribute("type",type);
        if(id!=null){
            model.addAttribute("data", getService().findById(id));
        }else{
            model.addAttribute("data", getService().findById(1));
        }
        return getTemplatePath("edit");
    }

}
