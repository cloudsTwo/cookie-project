package com.chinasoft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* 注册拦截器
*/
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
	String uploadPath = "";

    @Autowired
    private LoginInterceptor loginHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(loginHandlerInterceptor)
        .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
    }
    
    @Bean
    public LoginInterceptor loginHandlerInterceptor() {
        return new LoginInterceptor();
    }
    
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
		if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
			//如果是windows,就弄成windows的路径
			uploadPath = "D:/vue_workspace/";
		}
		
		//最后把img下的目录映射到指点路径下
		registry.addResourceHandler("/vue_workspace/**").addResourceLocations("file:///"+uploadPath);
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
}