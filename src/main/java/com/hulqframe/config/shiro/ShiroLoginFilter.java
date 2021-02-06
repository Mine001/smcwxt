package com.hulqframe.config.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断是否登录的过滤器，被Shiro组件调用使用
 */
public class ShiroLoginFilter  extends AdviceFilter {

    /**
     *
     * @param request
     * @param response
     * @return true-通过验证，false-验证失败
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        Object loginUser = SecurityUtils.getSubject().getPrincipal();
        if (null == loginUser) {
            HttpServletResponse res= (HttpServletResponse) response;
            res.sendRedirect("/login");
            return false;
        }
        return true;
    }
}

