package com.hulqframe.api.cwgl;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.cwgl.model.Supplier;
import com.hulqframe.cwgl.service.SupplierService;
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
@RequestMapping("/api/cwgl/supplier")
@Api
public class SupplierApiController{
    @Autowired
    private SupplierService supplierService;

    public BaseService<Supplier, Integer> getService() {
        return supplierService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取供应商表列表",notes = "获取供应商表列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<Supplier> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = supplierService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取供应商表详情",notes = "获取供应商表详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        Supplier supplier=supplierService.findById(id);
        return ApiResult.toSuccess(supplier);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存供应商表对象",notes = "保存供应商表对象",httpMethod = "POST")
    public  ApiResult  save(Supplier supplier){
        if(supplier.getId()==null){
            supplierService.save(supplier);
        }else{
            supplierService.update(supplier);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除供应商表",notes = "删除供应商表",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        supplierService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
