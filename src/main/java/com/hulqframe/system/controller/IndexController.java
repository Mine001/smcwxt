package com.hulqframe.system.controller;

import com.hulqframe.config.shiro.CustomizedToken;
import com.hulqframe.config.shiro.LoginType;
import com.hulqframe.config.shiro.PasswordHelper;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.model.User;
import com.hulqframe.system.service.PermissionService;
import com.hulqframe.system.service.RoleService;
import com.hulqframe.system.service.UserService;
import com.hulqframe.utils.Result;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @RequestMapping()
    public String index(){
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "index";
    }
    @RequestMapping("/unauthorized")
    public String unauthorized(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("");
        response.getWriter().println("<script type='text/javascript'>");
        response.getWriter().println("alert('您没有权限操作该菜单');");
        response.getWriter().println("top.location.href='/login';");
        response.getWriter().println("</script>");
        return null;
    }
    @PostMapping("/login")
    @ResponseBody
    public Result doLogin(@RequestParam String account,
                        @RequestParam String password){

        Result result=Result.SUCCESS;
        User user=userService.findByAccount(account);
        if(user==null){
            result=Result.FAILD;
            result.setMessage("账号或密码错误！");
            return result;
        }
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
         String newPassword = new SimpleHash(PasswordHelper.ALGORITHM_NAME, password,
                ByteSource.Util.bytes(user.getCredentialsSalt()), PasswordHelper.HASH_ITERATIONS).toHex();
        CustomizedToken usernamePasswordToken = new CustomizedToken(
                account,newPassword, LoginType.ADMIN.toString()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            result=Result.FAILD;
            result.setMessage("账号或密码错误！");
        } catch (AuthorizationException e) {
            e.printStackTrace();
            result=Result.FAILD;
            result.setMessage("没有权限！");
        }
        return result;
    }
}
