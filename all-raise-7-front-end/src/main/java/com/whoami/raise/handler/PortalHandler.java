package com.whoami.raise.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ǰ��ǰ����ҳ�ķ���
 * @author whoami
 *
 */
@Controller
public class PortalHandler {

	@RequestMapping(value = "/index.html")
	public String showPortalPage() {
		
		// ������ʵ���ݣ���ҳ������ʾ
		return "portal-page";
	}
}
