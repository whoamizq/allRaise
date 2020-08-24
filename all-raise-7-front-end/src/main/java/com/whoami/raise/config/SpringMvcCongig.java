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
		// 跳转到登录页面
		String urlPath = "/member/to/login/page.html";
		String viewName = "member-manager";
		registry.addViewController(urlPath).setViewName(viewName);
		// 跳转到个人中心
		urlPath = "/member/to/member/center/page.html";
		viewName = "member-center";
		registry.addViewController(urlPath).setViewName(viewName);
		// 跳转到同意协议页面
		urlPath = "/project/to/agree/page";
		viewName = "project-1-start";
		registry.addViewController(urlPath).setViewName(viewName);
	}
}
