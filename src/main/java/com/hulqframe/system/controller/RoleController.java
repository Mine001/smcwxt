package com.hulqframe.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.model.Role;
import com.hulqframe.system.service.PermissionService;
import com.hulqframe.system.service.RoleService;
import com.hulqframe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController<Role,Integer> {
    public final static Integer MENU_ROOT_ID=1;
    @Autowired
    private RoleService roleService;
    @Autowired
    @Lazy
    private PermissionService permissionService;

    @Override
    public BaseService<Role, Integer> getService() {
        return roleService;
    }
    @RequestMapping("config")
    public String config(ServletRequest request, Model model,Integer roleId){

        List<Permission> rolePermission= permissionService.listByRoleId(roleId);
        List<SearchBean> searchBeans=new ArrayList<>();
        searchBeans.add(new SearchBean(false,"SEARCH_AND_isDelete_EQ"));
        List<Permission> permissions=permissionService.listByParams(searchBeans,"seq");
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        buildTree(permissions,list,MENU_ROOT_ID,rolePermission);
        model.addAttribute("allPermission", JSONArray.toJSONString(list));
        model.addAttribute("roleId", roleId);
        return getTemplatePath("config");
    }

    public static List<Map<String,Object>> buildTree(List<Permission> permissions,
                                                     List<Map<String, Object>> list, Integer parentId,
                                                     List<Permission> rolePermission){
        for(Permission permission:permissions){
            if(permission.getParentId()!=null&&permission.getParentId().equals(parentId)){

                Map<String,Object> map=new HashMap<String,Object>();
                map.put("title",permission.getName());
                map.put("id",permission.getId());
                map.put("spread",true);
//                map.put("pId",permission.getParentId());
                if(rolePermission!=null&&rolePermission.contains(permission)){
                    Map<String,Object> state=new HashMap<>();
                    map.put("checked",true);
                }
                List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
                buildTree(permissions,children,permission.getId(), rolePermission);
                if(children.size()>0){
                    map.put("children",children);
                }
                list.add(map);
            }
        }
        return list;
    }


    @RequestMapping("saveConfig")
    @ResponseBody
    public Result saveConfig(ServletRequest request, Model model, Integer roleId,
                             Integer[] permissionIds){

        if(roleService.updateRolePermission(roleId,permissionIds)){
            return Result.SUCCESS;
        }else{
            return Result.FAILD;
        }

    }
}
