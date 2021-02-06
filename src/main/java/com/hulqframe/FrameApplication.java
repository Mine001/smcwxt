package com.hulqframe;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableOpenApi
@EnableKnife4j
@MapperScan("com.hulqframe.*.mapper")
//@ComponentScan(basePackages = "com.hulqframe.**")
//@EnableScheduling
//@EnableSwagger2
@EnableTransactionManagement
@EnableCaching
public class FrameApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FrameApplication.class, args);
	}

}
