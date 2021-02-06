package com.hulqframe.api.cwgl;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.cwgl.model.Payment;
import com.hulqframe.cwgl.service.PaymentService;
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
@RequestMapping("/api/cwgl/payment")
@Api
public class PaymentApiController{
    @Autowired
    private PaymentService paymentService;

    public BaseService<Payment, Integer> getService() {
        return paymentService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取付款记录列表",notes = "获取付款记录列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<Payment> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = paymentService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取付款记录详情",notes = "获取付款记录详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        Payment payment=paymentService.findById(id);
        return ApiResult.toSuccess(payment);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存付款记录对象",notes = "保存付款记录对象",httpMethod = "POST")
    public  ApiResult  save(Payment payment){
        if(payment.getId()==null){
            paymentService.save(payment);
        }else{
            paymentService.update(payment);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除付款记录",notes = "删除付款记录",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        paymentService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
