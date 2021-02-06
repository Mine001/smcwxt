package com.hulqframe.base.asp.resolver;

import com.hulqframe.api.vo.MemberVo;
import com.hulqframe.base.asp.CurrentMember;
import com.hulqframe.base.common.Constants;
import com.hulqframe.member.model.Member;
import com.hulqframe.system.model.User;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component //注意：Bean后置处理器本身也是一个Bean
public class CurrentMemberHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return  methodParameter.getParameterAnnotation(CurrentMember.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer
            modelAndViewContainer, NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        CurrentMember currentMember = methodParameter.getParameterAnnotation(CurrentMember.class);
        if(currentMember!=null){
            if(methodParameter.getParameterType().getName().equals(MemberVo.class.getName())){
                Member member= (Member) nativeWebRequest.getAttribute(Constants.LOGIN_MEMBER, RequestAttributes.SCOPE_REQUEST);
                MemberVo memberVo=new MemberVo();
                PropertyUtils.copyProperties(memberVo,member);
                return memberVo;
            }
        }
       return null;
    }
}
