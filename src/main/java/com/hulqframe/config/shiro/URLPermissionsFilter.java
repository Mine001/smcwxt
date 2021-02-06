package com.hulqframe.config.shiro;


import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
/**
 * 通过字符串验证权限
 * @author chenyd
 * 2017年11月21日
 */
public class URLPermissionsFilter extends PermissionsAuthorizationFilter {

    /**
     * mappedValue 访问该url时需要的权限
     * subject.isPermitted 判断访问的用户是否拥有mappedValue权限
     * 重写拦截器，只要符合配置的一个权限，即可通过
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws IOException {

        Subject subject = getSubject(request, response);
        // DefaultFilterChainManager
        // PathMatchingFilterChainResolver
        String[] perms = (String[]) mappedValue;
        boolean isPermitted = false;
        if (perms != null && perms.length > 0) {
            for (String str : perms) {
                if (subject.isPermitted(str)) {
                    isPermitted = true;
                    break;
                }
            }
        }

        return isPermitted;
    }
}
