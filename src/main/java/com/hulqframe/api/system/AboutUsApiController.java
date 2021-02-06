package com.hulqframe.api.system;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.system.model.AboutUs;
import com.hulqframe.system.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.hulqframe.base.ApiResult;
import java.util.List;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.base.asp.SearchBeanFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/aboutUs")
@Api
public class AboutUsApiController{
    @Autowired
    private AboutUsService aboutUsService;

    public BaseService<AboutUs, Integer> getService() {
        return aboutUsService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取关于我们列表",notes = "获取关于我们列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<AboutUs> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = aboutUsService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取关于我们详情",notes = "获取关于我们详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        AboutUs aboutUs=aboutUsService.findById(id);
        return ApiResult.toSuccess(aboutUs);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存关于我们对象",notes = "保存关于我们对象",httpMethod = "POST")
    public  ApiResult  save(AboutUs aboutUs){
        if(aboutUs.getId()==null){
            aboutUsService.save(aboutUs);
        }else{
            aboutUsService.update(aboutUs);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除关于我们",notes = "删除关于我们",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        aboutUsService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
