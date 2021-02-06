package com.hulqframe.system.controller;

import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.base.common.Constants;
import com.hulqframe.config.shiro.PasswordHelper;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.model.User;
import com.hulqframe.system.service.AboutUsService;
import com.hulqframe.system.service.PermissionService;
import com.hulqframe.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemController {
    public final static Integer MENU_ROOT_ID=1;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AboutUsService aboutUsService;

    @RequestMapping("/dashboard")
    public String dashboard(@CurrentUser User user, Model model){
        //添加用户认证信息
//        Subject loginUser = SecurityUtils.getSubject();
//        User user=userService.findByAccount((String) loginUser.getPrincipal());
//
//        List<Permission> permissions=permissionService.listByUserId(user.getId());
        List<Map<String,Object>> menus=new ArrayList<>();
        menus=buildTree(user.getPermissions(),menus,MENU_ROOT_ID);
        model.addAttribute("menus",menus);
        model.addAttribute("aboutUs",aboutUsService.findById(1));
        return "system/dashboard";
    }

    public static List<Map<String,Object>> buildTree(List<Permission> permissions,
                                                     List<Map<String,Object>> list, Integer parentId){

        for(Permission permission:permissions){
            if(permission.getType()!=null&&(
                    permission.getType().equals(Constants.PERMISSION_TYPE.ACTION.getValue())
                    ||permission.getType().equals(Constants.PERMISSION_TYPE.PERM.getValue())
                    )){//去除操作类型
                continue;
            }
            if(permission.getParentId()!=null&&permission.getParentId().equals(parentId)){
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("name",permission.getName());
                map.put("url",permission.getUrl());
                map.put("id",permission.getId());
                map.put("parentId",permission.getParentId());
                map.put("action",permission.getAction());
                map.put("icon",permission.getIcon());
                List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
                map.put("children",buildTree(permissions,children,permission.getId()));
                list.add(map);
            }
        }
        return list;
    }
}
