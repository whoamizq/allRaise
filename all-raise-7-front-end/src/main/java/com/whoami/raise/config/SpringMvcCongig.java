package com.whoami.raise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跳转登录页面，使用view-controller
 * @author whoami
 *
 */
@Configuration
public class SpringMvcCongig implements WebMvcConfigurer{
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 相当于：<mvc:view-controller path="/to/login/page.html" view-name="member-login" />
		
		String urlPath = "/member/to/login/page.html";
		String viewName = "member-manager";
		registry.addViewController(urlPath).setViewName(viewName);
		
		urlPath = "/member/to/member/center/page.html";
		viewName = "member-center";
		registry.addViewController(urlPath).setViewName(viewName);
	}
}
