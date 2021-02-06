package com.hulqframe.config;

import com.hulqframe.base.asp.interceptor.RequireLoginInterceptor;
import com.hulqframe.base.asp.resolver.CurrentMemberHandlerMethodArgumentResolver;
import com.hulqframe.base.asp.resolver.CurrentUserHandlerMethodArgumentResolver;
import com.hulqframe.base.asp.resolver.SearchBeanFilterHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;


import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
      @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new SearchBeanFilterHandlerMethodArgumentResolver());
        argumentResolvers.add(new CurrentUserHandlerMethodArgumentResolver());
        argumentResolvers.add(new CurrentMemberHandlerMethodArgumentResolver());
    }
    @Bean
    public RequireLoginInterceptor getRequireLoginInterceptor() {
        return new RequireLoginInterceptor();
    }
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        InterceptorRegistration addInterceptor =registry.addInterceptor(getRequireLoginInterceptor());
        addInterceptor.addPathPatterns("/api/**");
    }


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/**")
                .addResourceLocations("classpath:/static/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/");
        super.addResourceHandlers(registry);
    }

    /********************日期转换器*************************/
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;
    @PostConstruct //spring容器初始化的时候执行该方法
    public void initEditableValidation(){
        ConfigurableWebBindingInitializer initializer=(ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService()!=null){
            GenericConversionService genericConversionService=(GenericConversionService) initializer.getConversionService();
            genericConversionService.addConverter(new StringToDateConverter());
        }
    }
}
