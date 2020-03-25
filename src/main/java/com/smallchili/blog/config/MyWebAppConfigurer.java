package com.smallchili.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* @author xmz
* 2020-03-10
* 
*/
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
	
	@Value("${imagePath}")
	private String IMAGE_PATH;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+IMAGE_PATH);
    }
}