package com.whoami.raise.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RaiseTest {
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Test
	public void testSaveValueToRedisByRedisTemplate() {
		//��ȡRedis������
		ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
		
		//����ֵ
//		operations.set("keytest", "valuetest");
		
		//��ȡֵ
		Object object = operations.get("keytest");
		System.out.println(object);
	}
	
	@Test
	public void testSaveAndGetValueToRedisByStringRedisTemplate() {
		
		// ��ȡRedis������
		ValueOperations<String, String> operator = stringRedisTemplate.opsForValue();
		
		// ����ֵ
//		 operator.set("keytwo", "valuetwo");
		
		// ��ȡֵ
		String value = operator.get("keytwo");
		System.out.println(value);
	}
}
