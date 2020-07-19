package com.whoami.raise.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.aliyun.api.gateway.demo.util.HttpUtils;

public class RaiseUtil {
	/**
	 * �����û���¼�ɹ���ʹ�õ�token
	 * @return
	 */
	public static String generateToken() {
		return RaiseConstant.REDIS_MEMBER_SING_TOKEN_PREFIX + UUID.randomUUID().toString().replaceAll("-","");
	}
	
	/**
	 * ��֤�����Ƿ���Ч
	 * @param c		����֤����
	 * @return		��֤�����true����Ч��false����Ч��
	 * @author ���
	 */
	public static <E> boolean collectionEffectiveCheck(Collection<E> c) {
		return (c != null) && (c.size() > 0);
	}
	
	/**
	 * ��֤�ַ����Ƿ���Ч
	 * @param source	����֤�ַ���
	 * @return			��֤�����true����Ч��false����Ч��
	 * @author ���
	 */
	public static boolean strEffectiveCheck(String source) {
		return (source != null) && (source.length() > 0);
	}
	
	/**
	 * ���������֤��
	 * @param length	��֤�볤��
	 * @return			���ɵ���֤��
	 * @throws	RuntimeException ��֤�볤�ȱ������0
	 * @author ���
	 */
	public static String randomCode(int length) {
		
		if(length <= 0) {
			throw new RuntimeException(RaiseConstant.MESSAGE_RANDOM_CODE_LENGTH_INVALID);
		}
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < length; i++) {
			
			// 1.���������
			double doubleRandom = Math.random();
			
			// 2.����
			int integerRandom = (int) (doubleRandom * 10);
			
			// 3.ƴ��
			builder.append(integerRandom);
		}
		
		return builder.toString();
	}
	
	/**
	 * ������֤�����
	 * @param appcode		�������г��е���APIʱʶ����ݵ�appCode
	 * @param randomCode	��֤��ֵ
	 * @param phoneNum		������֤����ŵ��ֻ���
	 */
	public static void sendShortMessage(String appcode, String randomCode, String phoneNum) {
		// ���ö��ŷ��ͽӿ�ʱ�ķ��ʵ�ַ
		String host = "https://feginesms.market.alicloudapi.com";
		
		// �������·��
		String path = "/codeNotice";
		
		// ����ʽ
		String method = "GET";
		
		// ��¼�����ƺ󣬽���������̨->���г�->�ѹ�����񣬸���AppCode
		// String appcode = "61f2eaa3c43e42ad82c26fbbe1061311";
		Map<String, String> headers = new HashMap<String, String>();
		
		// �����header�еĸ�ʽ(�м���Ӣ�Ŀո�)ΪAuthorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		
		// ��װ�������
		Map<String, String> querys = new HashMap<String, String>();
		
		// ��֤��
		querys.put("param", randomCode);
		
		// ���ն��ŵ��ֻ���
		querys.put("phone", phoneNum);
		
		// ǩ�����
		querys.put("sign", "1");
		
		// ģ����
		querys.put("skin", "1");
		
		// JDK 1.8ʾ�����������������أ� http://code.fegine.com/Tools.zip
		
		try {
			/**
			 * ��Ҫ��ʾ����: HttpUtils���
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * ����ֱ�����أ� http://code.fegine.com/HttpUtils.zip ����
			 *
			 * ��Ӧ�����������
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 * ���jar������pom��ֱ�����أ� http://code.fegine.com/aliyun-jar.zip
			 */
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			// System.out.println(response.toString());�粻���json, ������д��룬��ӡ����ͷ��״̬�롣
			// ״̬��: 200 ������400 URL��Ч��401 appCode���� 403 �������ꣻ 500 API���ܴ���
			// ��ȡresponse��body
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new RuntimeException(e.getMessage());
		}
	}
}
