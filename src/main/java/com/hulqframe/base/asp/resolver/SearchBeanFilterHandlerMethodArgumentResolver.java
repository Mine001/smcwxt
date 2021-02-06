package com.hulqframe.base.asp.resolver;

import com.hulqframe.base.asp.SearchBeanFilter;
import com.hulqframe.base.common.SearchBean;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component //注意：Bean后置处理器本身也是一个Bean
public class SearchBeanFilterHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return  methodParameter.getParameterAnnotation( SearchBeanFilter.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer
            modelAndViewContainer, NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        SearchBeanFilter requestJson = methodParameter.getParameterAnnotation(SearchBeanFilter.class);
        List<SearchBean> searchBeans=new ArrayList<>();
        Iterator<String> parameterNames =nativeWebRequest.getParameterNames();
        if(parameterNames!=null){
            while (parameterNames.hasNext()){
                String pname=parameterNames.next();
                if(pname.startsWith(SearchBean.SEARCH_CRITERIA)){
                    String vaule=nativeWebRequest.getParameter(pname);
                    if(vaule!=null&&!vaule.trim().equals("")){
                        SearchBean searchBean=new SearchBean(vaule,pname);
                        searchBeans.add(searchBean);
                    }

                }

            }

        }
        return searchBeans;
    }
}

