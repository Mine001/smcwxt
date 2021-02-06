package com.hulqframe.api.cwgl;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.cwgl.model.Receive;
import com.hulqframe.cwgl.service.ReceiveService;
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
@RequestMapping("/api/cwgl/receive")
@Api
public class ReceiveApiController{
    @Autowired
    private ReceiveService receiveService;

    public BaseService<Receive, Integer> getService() {
        return receiveService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取收款记录列表",notes = "获取收款记录列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<Receive> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = receiveService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取收款记录详情",notes = "获取收款记录详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        Receive receive=receiveService.findById(id);
        return ApiResult.toSuccess(receive);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存收款记录对象",notes = "保存收款记录对象",httpMethod = "POST")
    public  ApiResult  save(Receive receive){
        if(receive.getId()==null){
            receiveService.save(receive);
        }else{
            receiveService.update(receive);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除收款记录",notes = "删除收款记录",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        receiveService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
