package com.hulqframe.api.cwgl;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.cwgl.model.OpenedInvoice;
import com.hulqframe.cwgl.service.OpenedInvoiceService;
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
@RequestMapping("/api/cwgl/openedInvoice")
@Api
public class OpenedInvoiceApiController{
    @Autowired
    private OpenedInvoiceService openedInvoiceService;

    public BaseService<OpenedInvoice, Integer> getService() {
        return openedInvoiceService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取开票记录列表",notes = "获取开票记录列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<OpenedInvoice> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = openedInvoiceService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取开票记录详情",notes = "获取开票记录详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        OpenedInvoice openedInvoice=openedInvoiceService.findById(id);
        return ApiResult.toSuccess(openedInvoice);
    }
//    @RequestMapping("save")
//    @ApiOperation(value = "保存开票记录对象",notes = "保存开票记录对象",httpMethod = "POST")
//    public  ApiResult  save(OpenedInvoice openedInvoice){
//        if(openedInvoice.getId()==null){
//            openedInvoiceService.save(openedInvoice);
//        }else{
//            openedInvoiceService.update(openedInvoice);
//        }
//        return ApiResult.toSuccess(null);
//    }
//    @RequestMapping("delete")
//    @ApiOperation(value = "删除开票记录",notes = "删除开票记录",httpMethod = "POST")
//    public  ApiResult  delete(Integer id){
//        openedInvoiceService.delete(id);
//        return ApiResult.toSuccess(null);
//    }

}
