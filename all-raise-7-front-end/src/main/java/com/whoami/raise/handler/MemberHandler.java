package com.whoami.raise.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberHandler {
	
	/**
	 * Ç°ÍùµÇÂ¼Ò³Ãæ
	 * @return
	 */
	@RequestMapping("/to/login/page.html")
	public String toLoginPage() {
		return "member-login";
	}
}
