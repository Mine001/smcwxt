package com.hulqframe.api.cwgl;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.cwgl.model.ProjectPayment;
import com.hulqframe.cwgl.service.ProjectPaymentService;
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
@RequestMapping("/api/cwgl/projectPayment")
@Api
public class ProjectPaymentApiController{
    @Autowired
    private ProjectPaymentService projectPaymentService;

    public BaseService<ProjectPayment, Integer> getService() {
        return projectPaymentService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取项目支出明细列表",notes = "获取项目支出明细列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<ProjectPayment> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = projectPaymentService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取项目支出明细详情",notes = "获取项目支出明细详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        ProjectPayment projectPayment=projectPaymentService.findById(id);
        return ApiResult.toSuccess(projectPayment);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存项目支出明细对象",notes = "保存项目支出明细对象",httpMethod = "POST")
    public  ApiResult  save(ProjectPayment projectPayment){
        if(projectPayment.getId()==null){
            projectPaymentService.save(projectPayment);
        }else{
            projectPaymentService.update(projectPayment);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除项目支出明细",notes = "删除项目支出明细",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        projectPaymentService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
