package com.hulqframe.api.system;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.system.model.Area;
import com.hulqframe.system.service.AreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/area")
@Api
public class AreaApiController{
    @Autowired
    private AreaService areaService;

    public BaseService<Area, Integer> getService() {
        return areaService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取区域管理列表",notes = "获取区域管理列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<Area> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = areaService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取区域管理详情",notes = "获取区域管理详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        Area area=areaService.findById(id);
        return ApiResult.toSuccess(area);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存区域管理对象",notes = "保存区域管理对象",httpMethod = "POST")
    public  ApiResult  save(Area area){
        if(area.getId()==null){
            areaService.save(area);
        }else{
            areaService.update(area);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除区域管理",notes = "删除区域管理",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        areaService.delete(id);
        return ApiResult.toSuccess(null);
    }

    @RequestMapping("getRegionName")
    @ApiOperation(value = "获取区域名称",notes = "获取区域名称")
    public  ApiResult  getRegionName(Integer id, @RequestParam(required = false)  Boolean isParent){
        return ApiResult.toSuccess(getName(id,"",isParent));
    }
    public String getName(Integer id,String regionName, Boolean isParent ){
        Area area=areaService.findById(id);
        if(area!=null){
            if(StringUtils.isEmpty(regionName)){
                regionName=area.getName();
            }else{
                regionName=area.getName()+"-"+regionName;
            }
            if(isParent){
                regionName=getName(area.getParentId(),regionName,isParent);
            }
        }

        return regionName;
    }
}
