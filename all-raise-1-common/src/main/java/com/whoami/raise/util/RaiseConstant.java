package com.whoami.raise.util;

import java.util.HashMap;
import java.util.Map;

public class RaiseConstant {
	public static final String REDIS_RANDOM_CODE_PREFIX = "RANDOM_CODE_"; //
	public static final String REDIS_MEMBER_SING_TOKEN_PREFIX = "SIGNED_MEMBER_";//�û���¼ǰ׺
	
	public static final String ATTR_NAME_MESSAGE = "MESSAGE";
	public static final String ATTR_NAME_LOGIN_ADMIN = "LOGIN-ADMIN";
	public static final String ATTR_NAME_PAGE_INFO = "PAGE-INFO";
	
	public static final String MESSAGE_LOGIN_FAILED = "��¼�˺Ż����벻��ȷ����˶Ժ��ٵ�¼��";
	public static final String MESSAGE_CODE_INVALID = "���Ĳ�����Ч�ַ�������˶Ժ��ٲ�����";
	public static final String MESSAGE_ACCESS_DENIED = "���¼���ٲ�����";
	public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "��¼�˺ű�ռ�ã��������趨��";
	
	public static final Map<String, String> EXCEPTION_MESSAGE_MAP = new HashMap<>();
	public static final String MESSAGE_RANDOM_CODE_LENGTH_INVALID = "��֤�볤�Ȳ��Ϸ���";

	public static final String MESSAGE_REDIS_KEY_OR_VALUE_INVALID = "������Redis��key��value������Ч�ַ�����";

	public static final String MESSAGE_REDIS_KEY_TIME_OUT_INVALID = "��Ǹ��������0��nullֵ������ȷ���������ͼ���Ƿ����ù���ʱ�䡣";

	public static final String MESSAGE_PHONE_NUM_INVALID = "�ֻ��Ų�����Ҫ��";

	public static final String MESSAGE_LOGINACCT_INVALID = "��¼�˺��ַ�����Ч��";

	public static final String MESSAGE_CODE_NOT_MATCH = "��֤�벻ƥ�䣡";

	public static final String MESSAGE_CODE_NOT_EXISTS = "��֤�벻���ڻ��ѹ��ڣ�";
	
	static {
		EXCEPTION_MESSAGE_MAP.put("java.lang.ArithmeticException", "ϵͳ�ڽ�����ѧ����ʱ��������");
		EXCEPTION_MESSAGE_MAP.put("java.lang.RuntimeException", "ϵͳ������ʱ��������");
		EXCEPTION_MESSAGE_MAP.put("com.atguigu.crowd.funding.exception.LoginException", "��¼���������д���");
		EXCEPTION_MESSAGE_MAP.put("org.springframework.security.access.AccessDeniedException", "�𾴵��û��������ڲ��߱����ʵ�ǰ���ܵ�Ȩ�ޣ�����ϵ��������Ա��");
	}
}
