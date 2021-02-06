package ${apiPackageName};

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import ${packageName}.model.${className};
import ${packageName}.service.${className}Service;
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

${"@"}RestController
${"@"}RequestMapping("${baseUrl}/${lowerClassName}")
${"@"}Api
public class ${className}ApiController{
    ${"@"}Autowired
    private ${className}Service ${lowerClassName}Service;

    public BaseService<${className}, Integer> getService() {
        return ${lowerClassName}Service;
    }

    ${"@"}RequestMapping("list")
    ${"@"}ApiOperation(value = "获取${dataTable.name}列表",notes = "获取${dataTable.name}列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<${className}> page,  ${"@"}SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = ${lowerClassName}Service.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    ${"@"}ApiOperation(value = "获取${dataTable.name}详情",notes = "获取${dataTable.name}详情",httpMethod = "POST")
    ${"@"}RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        ${className} ${lowerClassName}=${lowerClassName}Service.findById(id);
        return ApiResult.toSuccess(${lowerClassName});
    }
    ${"@"}RequestMapping("save")
    ${"@"}ApiOperation(value = "保存${dataTable.name}对象",notes = "保存${dataTable.name}对象",httpMethod = "POST")
    public  ApiResult  save(${className} ${lowerClassName}){
        if(${lowerClassName}.getId()==null){
            ${lowerClassName}Service.save(${lowerClassName});
        }else{
            ${lowerClassName}Service.update(${lowerClassName});
        }
        return ApiResult.toSuccess(null);
    }
    ${"@"}RequestMapping("delete")
    ${"@"}ApiOperation(value = "删除${dataTable.name}",notes = "删除${dataTable.name}",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        ${lowerClassName}Service.delete(id);
        return ApiResult.toSuccess(null);
    }

}
