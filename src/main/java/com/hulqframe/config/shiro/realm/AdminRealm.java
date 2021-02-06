package com.hulqframe.config.shiro.realm;

import com.hulqframe.system.model.Permission;
import com.hulqframe.system.model.Role;
import com.hulqframe.system.model.User;
import com.hulqframe.system.service.PermissionService;
import com.hulqframe.system.service.RoleService;
import com.hulqframe.system.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

public class AdminRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    private PermissionService permissionService;
    @Autowired
    @Lazy
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        User user = (User)principalCollection.getPrimaryPrincipal();
        //通过SimpleAuthenticationInfo做授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //添加权限
        for (Permission permission : user.getPermissions()) {
           simpleAuthorizationInfo.addStringPermission("perms_" + permission.getId());
        }
        //添加角色
        for(Role role:user.getRoles()){
            simpleAuthorizationInfo.addRole("ROLE_"+role.getId());
        }
//        simpleAuthorizationInfo.addRole("ADMIN");
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = userService.findByAccount(name);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            List<Permission> permissions=permissionService.listByUserId(user.getId());
            user.setPermissions(permissions);
            user.setRoles(roleService.listByUserId(user.getId()));
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
        }
    }
}
