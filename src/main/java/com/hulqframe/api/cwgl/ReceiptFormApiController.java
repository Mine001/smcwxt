package com.hulqframe.api.cwgl;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.cwgl.model.ReceiptForm;
import com.hulqframe.cwgl.service.ReceiptFormService;
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
@RequestMapping("/api/cwgl/receiptForm")
@Api
public class ReceiptFormApiController{
    @Autowired
    private ReceiptFormService receiptFormService;

    public BaseService<ReceiptForm, Integer> getService() {
        return receiptFormService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取签收单列表",notes = "获取签收单列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<ReceiptForm> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = receiptFormService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取签收单详情",notes = "获取签收单详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        ReceiptForm receiptForm=receiptFormService.findById(id);
        return ApiResult.toSuccess(receiptForm);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存签收单对象",notes = "保存签收单对象",httpMethod = "POST")
    public  ApiResult  save(ReceiptForm receiptForm){
        if(receiptForm.getId()==null){
            receiptFormService.save(receiptForm);
        }else{
            receiptFormService.update(receiptForm);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除签收单",notes = "删除签收单",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        receiptFormService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
