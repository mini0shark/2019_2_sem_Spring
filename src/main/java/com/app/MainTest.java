package com.app;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.app.config.JavaConfig;


public class MainTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(JavaConfig.class);
	}
}
