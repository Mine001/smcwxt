package com.hulqframe.config.shiro;


import com.hulqframe.config.shiro.realm.AdminRealm;
import com.hulqframe.config.shiro.jwt.JwtFilter;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.service.PermissionService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.context.annotation.Lazy;

import java.util.*;
import javax.servlet.Filter;

@Configuration
public class ShiroConfig {
    //不加这个注解不生效，具体不详
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //将自己的验证方式加入容器
    @Bean
    public Collection<Realm> myShiroRealms() {
        Collection<Realm> collection=new ArrayList<>();
        collection.add(getAdminRealm());
        return collection;
    }
    @Bean
    public PermissionService getPermissionService(){
        return  new PermissionService();
    }
    @Bean
    public AdminRealm getAdminRealm() {
        AdminRealm adminRealm = new AdminRealm();
        return adminRealm;
    }
    /**
     * 配置Shiro核心 安全管理器 SecurityManager
     * SecurityManager安全管理器：所有与安全有关的操作都会与SecurityManager交互；且它管理着所有Subject；负责与后边介绍的其他组件进行交互。
     * （类似于SpringMVC中的DispatcherServlet控制器）
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(myShiroRealms());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter>filters =new HashMap<>();
        filters.put("anon", new AnonymousFilter());
        filters.put("authc", new ShiroLoginFilter());
        filters.put("perms", new URLPermissionsFilter());

        shiroFilterFactoryBean.setFilters(filters);
        Map<String, String> map = new HashMap<>();
        //登出
        map.put("/logout", "logout");
        map.put("/unauthorized", "anon");
        map.put("/login", "anon");
        map.put("/api/**", "anon");
        map.put( "/swagger-ui/**", "anon");
        //对所有用户认证
//        map.put("/**", "auth");
        map.put("/system/dashboard", "authc");
        map.put("/system/**", "authc");

        shiroFilterFactoryBean.setLoginUrl("/login");

        //首页
        shiroFilterFactoryBean.setSuccessUrl("/system/dashboard");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");


        //注册 数据库中所有的权限 及其对应url
        List<Permission> allPermission = getPermissionService().listAll();//数据库中查询所有权限
        for (Permission p : allPermission) {
            map.put(p.getUrl(), "authc,perms[perms_" + p.getId() + "]");    //拦截器中注册所有的权限
        }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }
    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME); // 散列算法
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS); // 散列次数
        return hashedCredentialsMatcher;
    }


    @Bean
    public PasswordHelper passwordHelper() {
        return new PasswordHelper();
    }
}