package com.hulqframe.member.controller;

import com.alibaba.fastjson.JSONArray;
import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.member.model.MemberPermission;
import com.hulqframe.member.model.MemberRole;
import com.hulqframe.member.service.MemberPermissionService;
import com.hulqframe.member.service.MemberRoleService;
import com.hulqframe.system.model.Permission;
import com.hulqframe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/system/memberRole")
public class MemberRoleController extends BaseController<MemberRole,Integer> {
    public final static Integer MENU_ROOT_ID=1;
    @Autowired
    private MemberRoleService memberRoleService;
    @Autowired
    private MemberPermissionService memberPermissionService;
    @Override
    public BaseService<MemberRole, Integer> getService() {
    return memberRoleService;
    }
    @RequestMapping("config")
    public String config(ServletRequest request, Model model, Integer roleId){
        List<MemberPermission> rolePermission= memberPermissionService.listByRoleId(roleId);
        List<SearchBean> searchBeans=new ArrayList<>();
        searchBeans.add(new SearchBean(false,"SEARCH_AND_isDelete_EQ"));
        List<MemberPermission> permissions=memberPermissionService.listByParams(searchBeans,"seq");
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        buildTree(permissions,list,MENU_ROOT_ID,rolePermission);
        model.addAttribute("allPermission", JSONArray.toJSONString(list));
        model.addAttribute("roleId", roleId);
        return getTemplatePath("config");
    }

    public static List<Map<String,Object>> buildTree(List<MemberPermission> permissions,
                                                     List<Map<String, Object>> list, Integer parentId,
                                                     List<MemberPermission> rolePermission){
        for(MemberPermission permission:permissions){
            if(permission.getParentId()!=null&&permission.getParentId().equals(parentId)){

                Map<String,Object> map=new HashMap<String,Object>();
                map.put("text",permission.getName());
                map.put("id",permission.getId());
                map.put("pId",permission.getParentId());
                if(rolePermission!=null&&rolePermission.contains(permission)){
                    Map<String,Object> state=new HashMap<>();
                    state.put("checked",true);
                    map.put("state",state);
                }
                List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
                buildTree(permissions,children,permission.getId(), rolePermission);
                if(children.size()>0){
                    map.put("nodes",children);
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

        if(memberRoleService.updateRolePermission(roleId,permissionIds)){
            return Result.SUCCESS;
        }else{
            return Result.FAILD;
        }

    }


}
