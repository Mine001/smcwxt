package com.hulqframe.system.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.system.model.Area;
import com.hulqframe.system.model.User;
import com.hulqframe.system.service.AreaService;
import com.hulqframe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/area")
public class AreaController extends BaseController<Area,Integer> {
    @Autowired
    private AreaService areaService;
    @Override
    public BaseService<Area, Integer> getService() {
    return areaService;
    }
    @RequestMapping("/save")
    @ResponseBody
    public Result save(@CurrentUser User user, Model model, Integer id, Area entity) throws IOException {
        if(id!=null){
            getService().update(entity);
        }else{
            getService().save(entity);
        }
        areaService.initAreaData();
        return Result.SUCCESS;
    }
    @RequestMapping("listByPid")
    @ResponseBody
    public List<Area> listByPid() throws IOException {
        areaService.initAreaData();
        return areaService.listAll();
    }

//    public static List<Map<String,Object>> buildTree(List<Area> areas,
//                                                     List<Map<String,Object>> list, Integer parentId){
//
//        for(Area permission:areas){
//            if(permission.getParentId()!=null&&permission.getParentId().equals(parentId)){
//                Map<String,Object> map=new HashMap<String,Object>();
//                map.put("title",permission.getName());
//                map.put("id",permission.getId());
//                map.put("pId",permission.getParentId());
//                List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
//                buildTree(areas,children,permission.getId());
//                if(children.size()>0){
//                    map.put("nodes",children);
//                }
//                list.add(map);
//            }
//        }
//        return list;
//    }


}
