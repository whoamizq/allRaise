package com.whoami.raise.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whoami.raise.entity.ResultEntity;
/**
 * RedisԶ�̵��÷���
 * @author whoami
 *
 */
@FeignClient(value = "redis-provider")
public interface RedisOperationRemoteService {
	/**
	 * ���ַ������͵ļ�ֵ�Ա��浽Redisʱ���õ�Զ�̷���
	 * @param normalKey
	 * @param normalValue
	 * @param timeoutMinute ��ʱʱ�䣨��λ�����ӣ�
	 * 		-1��ʾ�޹���ʱ��
	 * 		������ʾ����ʱ�������
	 * 		0��nullֵ������
	 * @return
	 */
	@RequestMapping("/save/normal/string/key/value")
	ResultEntity<String> saveNormalStringKeyValue(@RequestParam("normalKey") String normalKey, @RequestParam("normalValue") String normalValue, @RequestParam("timeoutMinute") Integer timeoutMinute);
	
	/**
	 * ����key��ѯ��Ӧvalueʱ���õ�Զ�̷���
	 * @param normalKey
	 * @return
	 */
	@RequestMapping("/retrieve/string/value/by/string/key")
	ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey);
	
	/**
	 * ����keyɾ����Ӧvalueʱ���õ�Զ�̷���
	 * @param key
	 * @return
	 */
	@RequestMapping("/redis/remove/by/key")
	ResultEntity<String> removeByKey(@RequestParam("key") String key);
	
}
