package com.hulqframe.system.controller;

import com.github.pagehelper.PageInfo;
import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.common.LayUIPage;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.base.asp.SearchBeanFilter;
import com.hulqframe.base.common.Constants;
import com.hulqframe.system.model.Permission;

import com.hulqframe.system.service.PermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/permission")
public class PermissionController extends BaseController<Permission,Integer> {
    public final static Integer MENU_ROOT_ID=1;
    @Autowired
    private PermissionService permissionService;

    @Override
    public BaseService<Permission, Integer> getService() {
        return permissionService;
    }

    @RequestMapping("/listTree")
    @ResponseBody
    public LayUIPage  listTree(PageInfo<Permission> page, @SearchBeanFilter List<SearchBean> searchBeans, Model model,
                       String orderBy){
        List<Permission> permissions=permissionService.listByParams(searchBeans,orderBy);
//        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//        buildTree(permissions,list,MENU_ROOT_ID);
//        model.addAttribute("datas", JSONArray.toJSONString(list));
        LayUIPage layUIPage=new LayUIPage();
        layUIPage.setCode(0);
        layUIPage.setMsg("");
        layUIPage.setData(permissions);
        layUIPage.setCount(permissions.size());
        return layUIPage;
    }

    public static List<Map<String,Object>> buildTree(List<Permission> permissions,
                                                     List<Map<String,Object>> list, Integer parentId){

        for(Permission permission:permissions){
            if(permission.getParentId()!=null&&permission.getParentId().equals(parentId)){
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("text",permission.getName());
                map.put("id",permission.getId());
                map.put("pId",permission.getParentId());
                List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
                buildTree(permissions,children,permission.getId());
                if(children.size()>0){
                    map.put("nodes",children);
                }
                list.add(map);
            }
        }
        return list;
    }

    @RequestMapping("/editNew")
    public String editNew(Model model, @RequestParam(required = false) Integer id
            , @RequestParam(required = false) Integer parentId){
        Permission permission;
        if(id!=null){
            permission=getService().findById(id);
        }else{
            permission=new Permission();
            if(parentId!=null){
                permission.setParentId(parentId);
            }
        }
        model.addAttribute("types", Constants.PERMISSION_TYPE.values());
        model.addAttribute("data", permission);
        return getTemplatePath("edit");
    }
}
