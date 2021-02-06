package com.hulqframe.api.cwgl;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.cwgl.model.Project;
import com.hulqframe.cwgl.service.ProjectService;
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
@RequestMapping("/api/cwgl/project")
@Api
public class ProjectApiController{
    @Autowired
    private ProjectService projectService;

    public BaseService<Project, Integer> getService() {
        return projectService;
    }

    @RequestMapping("list")
    @ApiOperation(value = "获取项目表列表",notes = "获取项目表列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<Project> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = projectService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取项目表详情",notes = "获取项目表详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        Project project=projectService.findById(id);
        return ApiResult.toSuccess(project);
    }
    @RequestMapping("save")
    @ApiOperation(value = "保存项目表对象",notes = "保存项目表对象",httpMethod = "POST")
    public  ApiResult  save(Project project){
        if(project.getId()==null){
            projectService.save(project);
        }else{
            projectService.update(project);
        }
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("delete")
    @ApiOperation(value = "删除项目表",notes = "删除项目表",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        projectService.delete(id);
        return ApiResult.toSuccess(null);
    }

}
