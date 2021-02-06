package com.hulqframe.api.cwgl;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.cwgl.model.ReceivedInvoice;
import com.hulqframe.cwgl.service.ReceivedInvoiceService;
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
@RequestMapping("/api/cwgl/receivedInvoice")
@Api
public class ReceivedInvoiceApiController{
    @Autowired
    private ReceivedInvoiceService receivedInvoiceService;

    public BaseService<ReceivedInvoice, Integer> getService() {
        return receivedInvoiceService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取收票记录列表",notes = "获取收票记录列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<ReceivedInvoice> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = receivedInvoiceService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取收票记录详情",notes = "获取收票记录详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        ReceivedInvoice receivedInvoice=receivedInvoiceService.findById(id);
        return ApiResult.toSuccess(receivedInvoice);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存收票记录对象",notes = "保存收票记录对象",httpMethod = "POST")
    public  ApiResult  save(ReceivedInvoice receivedInvoice){
        if(receivedInvoice.getId()==null){
            receivedInvoiceService.save(receivedInvoice);
        }else{
            receivedInvoiceService.update(receivedInvoice);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除收票记录",notes = "删除收票记录",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        receivedInvoiceService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
