package com.hulqframe.member.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.asp.SearchBeanFilter;
import com.hulqframe.base.common.Constants;
import com.hulqframe.base.common.LayUIPage;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.member.model.MemberPermission;
import com.hulqframe.member.service.MemberPermissionService;
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
@RequestMapping("/system/memberPermission")
public class MemberPermissionController extends BaseController<MemberPermission,Integer> {
    public final static Integer MENU_ROOT_ID=1;
    @Autowired
    private MemberPermissionService memberPermissionService;
    @Override
    public BaseService<MemberPermission, Integer> getService() {
    return memberPermissionService;
    }
    @RequestMapping("/list")
    @ResponseBody
    @Override
    public LayUIPage list(PageInfo<MemberPermission> page, @SearchBeanFilter List<SearchBean> searchBeans, Model model,
                          String orderBy){
        List<MemberPermission> permissions=memberPermissionService.listByParams(searchBeans,orderBy);
        return LayUIPage.getLayUIPageByData(permissions);
    }

    public static List<Map<String,Object>> buildTree(List<MemberPermission> permissions,
                                                     List<Map<String,Object>> list, Integer parentId){

        for(MemberPermission permission:permissions){
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
        MemberPermission permission;
        if(id!=null){
            permission=getService().findById(id);
        }else{
            permission=new MemberPermission();
            if(parentId!=null){
                permission.setParentId(parentId);
            }else{
                permission.setParentId(MENU_ROOT_ID);
            }
        }
        model.addAttribute("types", Constants.MEMBER_PERMISSION_TYPE.values());
        model.addAttribute("data", permission);
        return getTemplatePath("edit");
    }


}
