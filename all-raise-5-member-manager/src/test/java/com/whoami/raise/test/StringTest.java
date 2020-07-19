package com.whoami.raise.test;

import org.junit.Test;

import com.whoami.raise.util.RaiseUtil;

/**
 * test测试短信服务
 * @author whoami
 *
 */
public class StringTest {
	
	@Test
	public void testSendCode() {
		String appcode = "aacb66ce05494adca01b1a238cdd4f45";
		String randomCode = RaiseUtil.randomCode(4);
		String phoneNum = "*这里填手机号*";
		RaiseUtil.sendShortMessage(appcode, randomCode, phoneNum);
	}
}
