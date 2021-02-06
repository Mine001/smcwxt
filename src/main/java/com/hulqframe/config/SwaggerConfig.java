package com.hulqframe.config;


import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API信息
     */
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("财务管理系统API")   //文档标题
                .description("财务管理系统API文档描述")    //文档描述
                //.termsOfServiceUrl("https://www.baidu.com/")  //服务协议
                .contact(contact())
                .version("1.0")
                .build();
    }

    /**
     * 联系人
     */
    Contact contact() {
        return new Contact("hulq", "", "");
    }


    List<SecurityContext> securityContextList() {
        return Stream.of(new SecurityContextBuilder()
                .securityReferences(securityReferenceList())
                .operationSelector(oper -> selector(oper))
                .build())
                .collect(Collectors.toList());
    }

    boolean selector(OperationContext operationContext) {
        String url = operationContext.requestMappingPattern();
        //这里可以写URL过滤规则
        return true;
    }


    private String SECURITY_SCHEME_TOKEN ="token";

    List<SecurityReference> securityReferenceList() {
        AuthorizationScope globalScope = new AuthorizationScope("global", "global scope");
        return Stream.of(new SecurityReference.SecurityReferenceBuilder()
                .reference(SECURITY_SCHEME_TOKEN)
                .scopes(new AuthorizationScope[]{globalScope})
                .build())
                .collect(Collectors.toList());
    }

    /**
     * 全局token配置
     */
    List<SecurityScheme> securitySchemeList() {
        return Stream.of(new ApiKey(SECURITY_SCHEME_TOKEN,SECURITY_SCHEME_TOKEN , "header"))
                .collect(Collectors.toList());
    }


    /**
     * 统一设置请求参数
     */
    List<RequestParameter> requestParameterList() {
        RequestParameter headParameter = new RequestParameterBuilder()
                .in(ParameterType.HEADER)
                .name(SECURITY_SCHEME_TOKEN)
                .required(true)
                .build();

        return Stream.of(headParameter).collect(Collectors.toList());
    }

    /**
     * 统一设置HTTP响应状态码及描述信息
     */
    List<Response> responseList() {
        return Stream.of(new ResponseBuilder().code("200").description("OK").build())
                .collect(Collectors.toList());
    }


}

