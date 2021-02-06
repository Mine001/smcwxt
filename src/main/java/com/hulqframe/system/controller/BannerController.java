package com.hulqframe.system.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.system.model.Banner;
import com.hulqframe.system.model.User;
import com.hulqframe.system.service.BannerService;
import com.hulqframe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/system/banner")
public class BannerController extends BaseController<Banner,Integer> {
    @Autowired
    private BannerService bannerService;
    @Override
    public BaseService<Banner, Integer> getService() {
    return bannerService;
    }
    @RequestMapping("/save")
    @ResponseBody
    public Result save(@CurrentUser User user, Model model, Integer id, Banner entity){
        if(id!=null){
            getService().update(entity);
        }else{
            entity.setCreateTime(new Date());
            entity.setCreateUser(user.getId());
            getService().save(entity);
        }

        return Result.SUCCESS;
    }


}
