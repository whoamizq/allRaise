package com.whoami.raise.controller;

import java.util.Objects;

import org.aspectj.lang.reflect.MemberSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whoami.raise.api.DataBaseOperationRemoteService;
import com.whoami.raise.api.RedisOperationRemoteService;
import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.po.MemberPO;
import com.whoami.raise.entity.vo.MemberSignSuccessVO;
import com.whoami.raise.entity.vo.MemberVO;
import com.whoami.raise.util.RaiseConstant;
import com.whoami.raise.util.RaiseUtil;

@RestController
@RequestMapping(value = "/member/manager")
public class MemberController {
	@Autowired
	private RedisOperationRemoteService redisRemoteServerice;
	
	@Autowired
	private DataBaseOperationRemoteService dataBaseOperationRemoteService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// Spring会根据@Value注解中的表达式读取yml配置文件给成员变量设置对应的值
	@Value("${raise.short.message.appCode}")
	private String appcode;
	
	/**
	 * 退出登录
	 * @param token
	 * @return
	 */
	@GetMapping(value = "/logout")
	public ResultEntity<String> logout(@RequestParam("token") String token){
		return redisRemoteServerice.removeByKey(token);
	}
	
	/**
	 * 登录功能-具体实现
	 * @param loginAcct
	 * @param userPswd
	 * @return
	 */
	@PostMapping(value = "/login")
	public ResultEntity<MemberSignSuccessVO> login(@RequestParam("loginAcct") String loginAcct,
			@RequestParam("userPswd") String userPswd){
		
		// 1. 根据登录账号查询数据库获取MemberPO对象
		ResultEntity<MemberPO> resultEntity = dataBaseOperationRemoteService.retrieveMemberByLoginAcct(loginAcct);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		
		// 2. 获取MemberPO对象
		MemberPO memberPO = resultEntity.getData();
		
		// 3. 检查MemberPO对象是否为空
		if(memberPO == null) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 4. 获取从数据库查询得到的密码
		String userpswdDatabase = memberPO.getUserpswd();
		// 5. 比较密码
		boolean matcheResult = passwordEncoder.matches(userPswd, userpswdDatabase);
		if(!matcheResult) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 6. 生成token
		String token = RaiseUtil.generateToken();
		
		// 7. 从MemberPO对象中获取memberId
		String memberId = memberPO.getId() + "";
		
		// 8. 将token和memberId存入Redis,设置30分钟
		ResultEntity<String> resultEntitySaveToken = redisRemoteServerice.saveNormalStringKeyValue(token, memberId, 30);
		if(ResultEntity.FAILED.equals(resultEntitySaveToken.getResult())) {
			return ResultEntity.failed(resultEntitySaveToken.getMessage());
		}
		
		// 9. 封装MemberSignSuccessVO对象
		MemberSignSuccessVO memberSignSuccessVO = new MemberSignSuccessVO();
		BeanUtils.copyProperties(memberPO, memberSignSuccessVO);
		memberSignSuccessVO.setToken(token);
		
		// 10. 返回结果
		return ResultEntity.successWithData(memberSignSuccessVO);
	}
	
	/**
	 * handler方法--执行注册
	 * @param memberVO
	 * @return
	 */
	@RequestMapping("/register")
	public ResultEntity<String> register(@RequestBody MemberVO memberVO){
		
		// 1. 检查验证码是否有效
		String randomCode = memberVO.getRandomCode();
		if(!RaiseUtil.strEffectiveCheck(randomCode)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_CODE_INVALID);
		}
		
		// 2. 检查手机号是否有效
		String phoneNum = memberVO.getPhoneNum();
		if(!RaiseUtil.strEffectiveCheck(phoneNum)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_PHONE_NUM_INVALID);
		}
		
		// 3.拼接随机验证码的key
		String randomCodeKey = RaiseConstant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
		
		// 远程调用redis-provider的方法获取对应的验证码值
		ResultEntity<String> resultEntity = redisRemoteServerice.retrieveStringValueByStringKey(randomCodeKey);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return resultEntity;
		}
		
		String randomCodeRedis = resultEntity.getData();
		// 5.没有查询到值：返回失败信息，停止执行
		if(randomCodeRedis == null) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_CODE_NOT_EXISTS);
		}
		
		// 6.进行比较
		if(!Objects.equals(randomCode, randomCodeRedis)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_CODE_NOT_MATCH);
		}
		
		// 7. 从Redis中删除当前key对应的验证码
		ResultEntity<String> resultEntityRemoveByKey = redisRemoteServerice.removeByKey(randomCodeKey);
		if(ResultEntity.FAILED.equals(resultEntityRemoveByKey.getResult())) {
			return resultEntityRemoveByKey;
		}
		
		// 8. 远程调用database-provider方法检查登录账号是否被占用
		String loginacct = memberVO.getLoginacct();
		ResultEntity<Integer> resultEntityRetrieveLoginAcctCount = dataBaseOperationRemoteService.retrieveLoignAcctCount(loginacct);
		if(ResultEntity.FAILED.equals(resultEntityRetrieveLoginAcctCount.getResult())) {
			return ResultEntity.failed(resultEntityRetrieveLoginAcctCount.getMessage());
		}
		
		Integer count = resultEntityRetrieveLoginAcctCount.getData();
		// 9. 已经被占用：返回失败信息，停止执行
		if(count > 0) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
		}
		
		// 10 密码加密
		String userpswd = memberVO.getUserpswd();
		String userpswdEncoded = passwordEncoder.encode(userpswd);
		memberVO.setUserpswd(userpswdEncoded);
		
		// 11. 远程调用database-provider方法执行保存操作
		MemberPO memberPO = new MemberPO();
		
		// 调用Spring提供的BeanUtils.copyProperties()工具类直接完成属性值注入
		BeanUtils.copyProperties(memberVO, memberPO);
		
		return dataBaseOperationRemoteService.saveMemberRemote(memberPO);
	}
	
	/**
	 * 发送验证码给用户并保存到redis
	 * @param phoneNum
	 * @return
	 */
	@RequestMapping("/send/code")
	public ResultEntity<String> sendCode(@RequestParam("phoneNum") String phoneNum){
		if(!RaiseUtil.strEffectiveCheck(phoneNum)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_PHONE_NUM_INVALID);
		}
		// 生成验证码
		int length = 4;
		String randomCode = RaiseUtil.randomCode(length);
		
		// 保存到redis
		Integer timeoutMinute = 15;
		String normalKey = RaiseConstant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
		ResultEntity<String> resultEntity = redisRemoteServerice.saveNormalStringKeyValue(normalKey, randomCode, timeoutMinute);
		
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return resultEntity;
		}
		
		// 发送验证码到用户手机
//		String appcode = "aacb66ce05494adca01b1a238cdd4f45";
		try {
			RaiseUtil.sendShortMessage(appcode, randomCode, phoneNum);
			return ResultEntity.successNoData();
		}catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}

}
