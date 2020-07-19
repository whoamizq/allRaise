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
		//获取Redis操作器
		ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
		
		//设置值
//		operations.set("keytest", "valuetest");
		
		//获取值
		Object object = operations.get("keytest");
		System.out.println(object);
	}
	
	@Test
	public void testSaveAndGetValueToRedisByStringRedisTemplate() {
		
		// 获取Redis操作器
		ValueOperations<String, String> operator = stringRedisTemplate.opsForValue();
		
		// 设置值
//		 operator.set("keytwo", "valuetwo");
		
		// 获取值
		String value = operator.get("keytwo");
		System.out.println(value);
	}
}
