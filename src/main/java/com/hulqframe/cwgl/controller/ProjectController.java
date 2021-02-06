package com.hulqframe.cwgl.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.cwgl.model.Project;
import com.hulqframe.cwgl.service.ProjectService;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cwgl/project")
public class ProjectController extends BaseController<Project,Integer> {
    @Autowired
    private ProjectService projectService;
    @Override
    public BaseService<Project, Integer> getService() {
    return projectService;
    }


    @RequestMapping("/listForSelect")
    public String listForSelect(@CurrentUser User user, ServletRequest request, Model model){
        setParameters(request,model);

        return getTemplatePath("listForSelect");
    }
}
