package com.whoami.raise.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberHandler {
	
	/**
	 * ǰ����¼ҳ��
	 * @return
	 */
	@RequestMapping("/to/login/page.html")
	public String toLoginPage() {
		return "member-login";
	}
}
