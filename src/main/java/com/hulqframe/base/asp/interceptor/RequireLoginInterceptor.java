package com.hulqframe.base.asp.interceptor;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hulqframe.base.ApiResult;
import com.hulqframe.base.asp.RequireLogin;
import com.hulqframe.base.common.Constants;
import com.hulqframe.base.exception.AuthException;
import com.hulqframe.config.shiro.jwt.JwtUtil;
import com.hulqframe.member.model.Member;
import com.hulqframe.member.service.MemberService;
import com.hulqframe.system.model.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 实现方法体登录验证
 *
 */
@Component //注意：Bean后置处理器本身也是一个Bean
public class RequireLoginInterceptor extends HandlerInterceptorAdapter {
    private final static String AUTH_HEADER="token";
    @Autowired
    private MemberService memberService;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireLogin auth = handlerMethod.getMethodAnnotation(RequireLogin.class);
        if(auth==null){

            return true;
        }

        response.setHeader("Content-type", "application/json;charset=UTF-8");  //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        response.setCharacterEncoding("UTF-8");

        String token=request.getHeader(AUTH_HEADER);

        if (token != null) {
            String account=JwtUtil.getUserName(token);
            if(account==null){
//                PrintWriter writer=response.getWriter();
//                ApiResult result=new ApiResult();
//                result.setMessage("无效token");
//                result.setStatusCode(403);
//                writer.write(JSONObject.toJSON(result).toString());//页面中文乱码
//                writer.flush();
//                return false;
                throw new AuthException(AuthException.AuthExceptionEnum.INVALID_TOKEN.getVal());
            }
            Member member=memberService.findByUnique("account",account);
            if(member==null){
//                PrintWriter writer=response.getWriter();
//                ApiResult result=new ApiResult();
//                result.setMessage("无效token");
//                result.setStatusCode(403);
//                writer.write(JSONObject.toJSON(result).toString());//页面中文乱码
//                writer.flush();
//                return false;
                throw new AuthException(AuthException.AuthExceptionEnum.INVALID_TOKEN.getVal());
            }
           request.setAttribute(Constants.LOGIN_MEMBER,member);
           if(auth.isPermission()){
               String apis=JwtUtil.getUserApis(token);
               //获取接口上的reqeustmapping注解
               RequestMapping mapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
               RequestMapping classMapping = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequestMapping.class);
                String url=null;
                if(classMapping!=null&&classMapping.value()!=null&& classMapping.value().length>0){
                    url=classMapping.value()[0];
                }
               if(mapping!=null&&mapping.value()!=null&& mapping.value().length>0){
                   url=url+"/"+mapping.value()[0];
               }

               if(apis.contains(url+";")){//包含该权限
                   return true;
               }else{
                   throw new AuthException(AuthException.AuthExceptionEnum.NO_PROMISE.getVal());
//                   PrintWriter writer=response.getWriter();
//                   request.setCharacterEncoding("UTF-8");
//                   response.setCharacterEncoding("UTF-8");
//                   ApiResult result=new ApiResult();
//                   result.setMessage("您没有访问权限");
//                   result.setStatusCode(500);
//                   writer.write(JSONObject.toJSON(result).toString());//页面中文乱码
//                   writer.flush();
//                   return false;
               }
           }else{
               return true;
           }
        } else {
//            PrintWriter writer=response.getWriter();
//            request.setCharacterEncoding("UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            ApiResult result=new ApiResult();
//            result.setMessage("请先登录");
//            result.setStatusCode(401);
//            writer.write(JSONObject.toJSON(result).toString());//页面中文乱码
//            writer.flush();
//            return false;
            throw new AuthException(AuthException.AuthExceptionEnum.NO_TOKEN.getVal());
        }
    }
}