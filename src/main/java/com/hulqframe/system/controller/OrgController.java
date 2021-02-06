package com.hulqframe.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.common.LayUIPage;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.base.asp.SearchBeanFilter;

import com.hulqframe.system.model.Org;

import com.hulqframe.system.service.OrgService;
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
@RequestMapping("/system/org")
public class OrgController extends BaseController<Org,Integer> {
    private final static Integer ORG_ROOT_ID=0;
    @Autowired
    private OrgService orgService;
    @Override
    public BaseService<Org, Integer> getService() {
        return orgService;
    }

    @RequestMapping("/listTree")
    @ResponseBody
    public LayUIPage listTree(PageInfo<Org> page, @SearchBeanFilter List<SearchBean> searchBeans, Model model,
                              String orderBy){
        List<Org> orgs=orgService.listByParams(searchBeans,orderBy);
//        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//        buildTree(orgs,list,ORG_ROOT_ID);
//        model.addAttribute("datas", JSONArray.toJSONString(list));
        LayUIPage layUIPage=new LayUIPage();
        layUIPage.setCode(0);
        layUIPage.setMsg("");
        layUIPage.setData(orgs);
        layUIPage.setCount(orgs.size());
        return layUIPage;
    }
    public static List<Map<String,Object>> buildTree(List<Org> orgs,
                                                     List<Map<String,Object>> list, Integer parentId){
        for(Org org:orgs){
            if(org.getParentId()!=null&&org.getParentId().equals(parentId)){
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("name",org.getOrgName());
                map.put("id",org.getId());
                map.put("code",org.getOrgCode());
                map.put("pId",org.getParentId());
                List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
                buildTree(orgs,children,org.getId());
//                if(children.size()>0){
//                    map.put("nodes",children);
//                }
                list.add(map);
            }
        }
        return list;
    }

    @RequestMapping("/editNew")
    public String editNew(Model model, @RequestParam(required = false) Integer id
            , @RequestParam(required = false) Integer parentId){
        Org org;
        if(id!=null){
            org=getService().findById(id);
        }else{
            org=new Org();
            if(parentId!=null){
                org.setParentId(parentId);
            }else{
                org.setParentId(ORG_ROOT_ID);
            }
        }
        model.addAttribute("data", org);
        return getTemplatePath("edit");
    }
}
