package com.hulqframe.api.system;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.system.model.DataDict;
import com.hulqframe.system.service.DataDictService;
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
@RequestMapping("/api/dataDict/dataDict")
@Api
public class DataDictApiController{
    @Autowired
    private DataDictService dataDictService;

    public BaseService<DataDict, Integer> getService() {
        return dataDictService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取数据字典列表",notes = "获取数据字典列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<DataDict> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = dataDictService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取数据字典详情",notes = "获取数据字典详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        DataDict dataDict=dataDictService.findById(id);
        return ApiResult.toSuccess(dataDict);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存数据字典对象",notes = "保存数据字典对象",httpMethod = "POST")
    public  ApiResult  save(DataDict dataDict){
        if(dataDict.getId()==null){
            dataDictService.save(dataDict);
        }else{
            dataDictService.update(dataDict);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除数据字典",notes = "删除数据字典",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        dataDictService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
