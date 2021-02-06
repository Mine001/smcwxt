package com.hulqframe.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.config.shiro.CustomizedToken;
import com.hulqframe.config.shiro.LoginType;
import com.hulqframe.config.shiro.PasswordHelper;
import com.hulqframe.system.model.Role;
import com.hulqframe.system.model.User;
import com.hulqframe.system.service.RoleService;
import com.hulqframe.system.service.UserService;
import com.hulqframe.utils.Result;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.util.List;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController<User,Integer> {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Override
    public BaseService<User, Integer> getService() {
        return userService;
    }
    @RequestMapping("/saveNew")
    @ResponseBody
    public Result saveNew(Model model, Integer id, User user,Integer[] roleIds){
        if(id!=null){
            getService().update(user);
        }else{
            PasswordHelper passwordHelper=new PasswordHelper();
            passwordHelper.encryptPassword(user);
            getService().save(user);
        }
        userService.updateUserRole(user.getId(),roleIds);
        return Result.SUCCESS;
    }
    @RequestMapping("/edit")
    public String edit(Model model, ServletRequest request, @RequestParam(required = false) Integer id){

        List<Role> roles=roleService.listAll();
        model.addAttribute("roles", roles);

        if(id!=null){
            model.addAttribute("data", getService().findById(id));
            List<Integer> roleIds=roleService.listRoleIdByUserId(id);
            model.addAttribute("roleIds", JSONArray.toJSON(roleIds));
            return getTemplatePath("edit");
        }else{
            return getTemplatePath("add");
        }


    }
    @RequestMapping("/checkAccount")
    @ResponseBody
    public Boolean checkAccount(String account){
        User user=userService.findByAccount(account);
        if(user!=null){
            return false;
        }else{
            return true;
        }
    }
    @RequestMapping("/delete")
    @ResponseBody
    @Override
    public Result delete(Model model, @RequestParam Integer id){
        User user=userService.findById(id);
        user.setIsDelete(true);
        userService.update(user);
        return Result.SUCCESS;
    }
    @RequestMapping("/editInfo")
    public String editInfo(@CurrentUser User user,Model model){
        model.addAttribute("data",userService.findById(user.getId()));
        return getTemplatePath("editInfo");
    }
    @RequestMapping("/updateInfo")
    @ResponseBody
    public Result updateInfo(@CurrentUser User user,User user2){
        user2.setId(user.getId());
        userService.update(user2);
        return Result.SUCCESS;
    }
    @RequestMapping("/editPass")
    public String editPass(@CurrentUser User user,Model model){
        return getTemplatePath("editPass");
    }
    @RequestMapping("/updatePass")
    @ResponseBody
    public Result updatePass(@CurrentUser User user,String newPass,String oldPass){
        user=userService.findById(user.getId());
        oldPass = new SimpleHash(PasswordHelper.ALGORITHM_NAME, oldPass,
                ByteSource.Util.bytes(user.getCredentialsSalt()), PasswordHelper.HASH_ITERATIONS).toHex();
        if(user.getPassword().equals(oldPass)){
            user.setPassword(newPass);
            PasswordHelper passwordHelper=new PasswordHelper();
            passwordHelper.encryptPassword(user);
            getService().update(user);
        }else{
            Result result=Result.FAILD;
            result.setMessage("原始密码错误");
            return result;
        }
        return Result.SUCCESS;
    }
}
