package com.whoami.raise.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前往前端首页的方法
 * @author whoami
 *
 */
@Controller
public class PortalHandler {

	@RequestMapping(value = "/index.html")
	public String showPortalPage() {
		
		// 加载真实数据，到页面上显示
		return "portal-page";
	}
}
