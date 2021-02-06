package com.hulqframe.api.cwgl;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.cwgl.model.ReceiptPayment;
import com.hulqframe.cwgl.service.ReceiptPaymentService;
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
@RequestMapping("/api/cwgl/receiptPayment")
@Api
public class ReceiptPaymentApiController{
    @Autowired
    private ReceiptPaymentService receiptPaymentService;

    public BaseService<ReceiptPayment, Integer> getService() {
        return receiptPaymentService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取签收单付款明细列表",notes = "获取签收单付款明细列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<ReceiptPayment> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = receiptPaymentService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取签收单付款明细详情",notes = "获取签收单付款明细详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        ReceiptPayment receiptPayment=receiptPaymentService.findById(id);
        return ApiResult.toSuccess(receiptPayment);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存签收单付款明细对象",notes = "保存签收单付款明细对象",httpMethod = "POST")
    public  ApiResult  save(ReceiptPayment receiptPayment){
        if(receiptPayment.getId()==null){
            receiptPaymentService.save(receiptPayment);
        }else{
            receiptPaymentService.update(receiptPayment);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除签收单付款明细",notes = "删除签收单付款明细",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        receiptPaymentService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
