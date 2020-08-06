package com.whoami.raise.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.util.RaiseConstant;
import com.whoami.raise.util.RaiseUtil;
/**
 * ������ʵ��RedisOperationService
 * @author whoami
 *
 */
@RestController
public class RedisOperationController {
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * ���ַ������͵ļ�ֵ�Ա��浽Redisʱ���õ�Զ�̷���
	 * @param normalKey
	 * @param normalValue
	 * @param timeoutMinute	��ʱʱ�䣨��λ�����ӣ�
	 * @return
	 */
	@RequestMapping("/save/normal/string/key/value")
	ResultEntity<String> saveNormalStringKeyValue(
			@RequestParam("normalKey") String normalKey, 
			@RequestParam("normalValue") String normalValue, 
			@RequestParam("timeoutMinute") Integer timeoutMinute){
		// ���������ݽ�����֤
		if(!RaiseUtil.strEffectiveCheck(normalKey) || !RaiseUtil.strEffectiveCheck(normalValue)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		
		// �Ի�ȡ�ַ�������������
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		
		// �ж�timeoutMinuteֵ���Ƿ�Ϊ��Чֵ
		if(timeoutMinute == null||timeoutMinute == 0) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_REDIS_KEY_TIME_OUT_INVALID);
		}
		
		// �ж�timeoutMinuteֵ���Ƿ�Ϊ�����ù���ʱ��
		if(timeoutMinute == -1) {
			// ���ղ����ù���ʱ��ķ�ʽִ�б���
			try {
				operations.set(normalKey, normalValue);
			}catch (Exception e) {
				e.printStackTrace();
				return ResultEntity.failed(e.getMessage());
			}
			// ���ؽ��
			return ResultEntity.successNoData();
		}
		
		// �������ù���ʱ��ķ�ʽִ�б���
		try {
			operations.set(normalKey, normalValue, timeoutMinute, TimeUnit.MINUTES);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
		
		return ResultEntity.successNoData();
		
	}
	
	/**
	 * ����key��ѯ��Ӧvalueʱ���õ�Զ�̷���
	 * @param normalKey
	 * @return
	 */
	@RequestMapping("/retrieve/string/value/by/string/key")
	ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey){
		// ���������ݽ�����֤
		if(!RaiseUtil.strEffectiveCheck(normalKey)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		
		try {
			String value = redisTemplate.opsForValue().get(normalKey);
			return ResultEntity.successWithData(value);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
		
	}
	
	/**
	 * ����keyɾ����Ӧvalueʱ���õ�Զ�̷���
	 * @param key
	 * @return
	 */
	@RequestMapping("/redis/remove/by/key")
	ResultEntity<String> removeByKey(@RequestParam("key") String key){
		// ���������ݽ�����֤
		if(!RaiseUtil.strEffectiveCheck(key)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		
		try {
			redisTemplate.delete(key);
			return ResultEntity.successNoData();
		}catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
}
