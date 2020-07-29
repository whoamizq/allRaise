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
 * 不建议实现RedisOperationService
 * @author whoami
 *
 */
@RestController
public class RedisOperationController {
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 将字符串类型的键值对保存到Redis时调用的远程方法
	 * @param normalKey
	 * @param normalValue
	 * @param timeoutMinute	超时时间（单位：分钟）
	 * @return
	 */
	@RequestMapping("/save/normal/string/key/value")
	ResultEntity<String> saveNormalStringKeyValue(
			@RequestParam("normalKey") String normalKey, 
			@RequestParam("normalValue") String normalValue, 
			@RequestParam("timeoutMinute") Integer timeoutMinute){
		// 对输入数据进行验证
		if(!RaiseUtil.strEffectiveCheck(normalKey) || !RaiseUtil.strEffectiveCheck(normalValue)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		
		// 对获取字符串操作器对象
		ValueOperations<String, String> operations = redisTemplate.opsForValue();
		
		// 判断timeoutMinute值：是否为无效值
		if(timeoutMinute == null||timeoutMinute == 0) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_REDIS_KEY_TIME_OUT_INVALID);
		}
		
		// 判断timeoutMinute值：是否为不设置过期时间
		if(timeoutMinute == -1) {
			// 按照不设置过期时间的方式执行保存
			try {
				operations.set(normalKey, normalValue);
			}catch (Exception e) {
				e.printStackTrace();
				return ResultEntity.failed(e.getMessage());
			}
			// 返回结果
			return ResultEntity.successNoData();
		}
		
		// 按照设置过期时间的方式执行保存
		try {
			operations.set(normalKey, normalValue, timeoutMinute, TimeUnit.MINUTES);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
		
		return ResultEntity.successNoData();
		
	}
	
	/**
	 * 根据key查询对应value时调用的远程方法
	 * @param normalKey
	 * @return
	 */
	@RequestMapping("/retrieve/string/value/by/string/key")
	ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey){
		// 对输入数据进行验证
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
	 * 根据key删除对应value时调用的远程方法
	 * @param key
	 * @return
	 */
	@RequestMapping("/redis/remove/by/key")
	ResultEntity<String> removeByKey(@RequestParam("key") String key){
		// 对输入数据进行验证
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
