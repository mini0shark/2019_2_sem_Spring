package com.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**")
		.addResourceLocations("classpath:/img/")
		.setCachePeriod(20);
		registry.addResourceHandler("/termProject-final/css/**")
		.addResourceLocations("classpath:/css/")
		.setCachePeriod(20);
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
