package com.whoami.raise.test;

import org.junit.Test;

import com.whoami.raise.util.RaiseUtil;

/**
 * test���Զ��ŷ���
 * @author whoami
 *
 */
public class StringTest {
	
	@Test
	public void testSendCode() {
		String appcode = "aacb66ce05494adca01b1a238cdd4f45";
		String randomCode = RaiseUtil.randomCode(4);
		String phoneNum = "*�������ֻ���*";
		RaiseUtil.sendShortMessage(appcode, randomCode, phoneNum);
	}
}
