package com.hulqframe.api.system;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.system.model.Banner;
import com.hulqframe.system.service.BannerService;
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
@RequestMapping("/api/banner")
@Api
public class BannerApiController{
    @Autowired
    private BannerService bannerService;

    public BaseService<Banner, Integer> getService() {
        return bannerService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取Banner管理列表",notes = "获取Banner管理列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<Banner> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = bannerService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取Banner管理详情",notes = "获取Banner管理详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        Banner banner=bannerService.findById(id);
        return ApiResult.toSuccess(banner);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存Banner管理对象",notes = "保存Banner管理对象",httpMethod = "POST")
    public  ApiResult  save(Banner banner){
        if(banner.getId()==null){
            bannerService.save(banner);
        }else{
            bannerService.update(banner);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除Banner管理",notes = "删除Banner管理",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        bannerService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
