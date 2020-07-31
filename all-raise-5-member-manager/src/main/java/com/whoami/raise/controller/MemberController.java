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
	
	// Spring�����@Valueע���еı��ʽ��ȡyml�����ļ�����Ա�������ö�Ӧ��ֵ
	@Value("${raise.short.message.appCode}")
	private String appcode;
	
	/**
	 * �˳���¼
	 * @param token
	 * @return
	 */
	@GetMapping(value = "/logout")
	public ResultEntity<String> logout(@RequestParam("token") String token){
		return redisRemoteServerice.removeByKey(token);
	}
	
	/**
	 * ��¼����-����ʵ��
	 * @param loginAcct
	 * @param userPswd
	 * @return
	 */
	@PostMapping(value = "/login")
	public ResultEntity<MemberSignSuccessVO> login(@RequestParam("loginAcct") String loginAcct,
			@RequestParam("userPswd") String userPswd){
		
		// 1. ���ݵ�¼�˺Ų�ѯ���ݿ��ȡMemberPO����
		ResultEntity<MemberPO> resultEntity = dataBaseOperationRemoteService.retrieveMemberByLoginAcct(loginAcct);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		
		// 2. ��ȡMemberPO����
		MemberPO memberPO = resultEntity.getData();
		
		// 3. ���MemberPO�����Ƿ�Ϊ��
		if(memberPO == null) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 4. ��ȡ�����ݿ��ѯ�õ�������
		String userpswdDatabase = memberPO.getUserpswd();
		// 5. �Ƚ�����
		boolean matcheResult = passwordEncoder.matches(userPswd, userpswdDatabase);
		if(!matcheResult) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 6. ����token
		String token = RaiseUtil.generateToken();
		
		// 7. ��MemberPO�����л�ȡmemberId
		String memberId = memberPO.getId() + "";
		
		// 8. ��token��memberId����Redis,����30����
		ResultEntity<String> resultEntitySaveToken = redisRemoteServerice.saveNormalStringKeyValue(token, memberId, 30);
		if(ResultEntity.FAILED.equals(resultEntitySaveToken.getResult())) {
			return ResultEntity.failed(resultEntitySaveToken.getMessage());
		}
		
		// 9. ��װMemberSignSuccessVO����
		MemberSignSuccessVO memberSignSuccessVO = new MemberSignSuccessVO();
		BeanUtils.copyProperties(memberPO, memberSignSuccessVO);
		memberSignSuccessVO.setToken(token);
		
		// 10. ���ؽ��
		return ResultEntity.successWithData(memberSignSuccessVO);
	}
	
	/**
	 * handler����--ִ��ע��
	 * @param memberVO
	 * @return
	 */
	@RequestMapping("/register")
	public ResultEntity<String> register(@RequestBody MemberVO memberVO){
		
		// 1. �����֤���Ƿ���Ч
		String randomCode = memberVO.getRandomCode();
		if(!RaiseUtil.strEffectiveCheck(randomCode)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_CODE_INVALID);
		}
		
		// 2. ����ֻ����Ƿ���Ч
		String phoneNum = memberVO.getPhoneNum();
		if(!RaiseUtil.strEffectiveCheck(phoneNum)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_PHONE_NUM_INVALID);
		}
		
		// 3.ƴ�������֤���key
		String randomCodeKey = RaiseConstant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
		
		// Զ�̵���redis-provider�ķ�����ȡ��Ӧ����֤��ֵ
		ResultEntity<String> resultEntity = redisRemoteServerice.retrieveStringValueByStringKey(randomCodeKey);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return resultEntity;
		}
		
		String randomCodeRedis = resultEntity.getData();
		// 5.û�в�ѯ��ֵ������ʧ����Ϣ��ִֹͣ��
		if(randomCodeRedis == null) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_CODE_NOT_EXISTS);
		}
		
		// 6.���бȽ�
		if(!Objects.equals(randomCode, randomCodeRedis)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_CODE_NOT_MATCH);
		}
		
		// 7. ��Redis��ɾ����ǰkey��Ӧ����֤��
		ResultEntity<String> resultEntityRemoveByKey = redisRemoteServerice.removeByKey(randomCodeKey);
		if(ResultEntity.FAILED.equals(resultEntityRemoveByKey.getResult())) {
			return resultEntityRemoveByKey;
		}
		
		// 8. Զ�̵���database-provider��������¼�˺��Ƿ�ռ��
		String loginacct = memberVO.getLoginacct();
		ResultEntity<Integer> resultEntityRetrieveLoginAcctCount = dataBaseOperationRemoteService.retrieveLoignAcctCount(loginacct);
		if(ResultEntity.FAILED.equals(resultEntityRetrieveLoginAcctCount.getResult())) {
			return ResultEntity.failed(resultEntityRetrieveLoginAcctCount.getMessage());
		}
		
		Integer count = resultEntityRetrieveLoginAcctCount.getData();
		// 9. �Ѿ���ռ�ã�����ʧ����Ϣ��ִֹͣ��
		if(count > 0) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
		}
		
		// 10 �������
		String userpswd = memberVO.getUserpswd();
		String userpswdEncoded = passwordEncoder.encode(userpswd);
		memberVO.setUserpswd(userpswdEncoded);
		
		// 11. Զ�̵���database-provider����ִ�б������
		MemberPO memberPO = new MemberPO();
		
		// ����Spring�ṩ��BeanUtils.copyProperties()������ֱ���������ֵע��
		BeanUtils.copyProperties(memberVO, memberPO);
		
		return dataBaseOperationRemoteService.saveMemberRemote(memberPO);
	}
	
	/**
	 * ������֤����û������浽redis
	 * @param phoneNum
	 * @return
	 */
	@RequestMapping("/send/code")
	public ResultEntity<String> sendCode(@RequestParam("phoneNum") String phoneNum){
		if(!RaiseUtil.strEffectiveCheck(phoneNum)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_PHONE_NUM_INVALID);
		}
		// ������֤��
		int length = 4;
		String randomCode = RaiseUtil.randomCode(length);
		
		// ���浽redis
		Integer timeoutMinute = 15;
		String normalKey = RaiseConstant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
		ResultEntity<String> resultEntity = redisRemoteServerice.saveNormalStringKeyValue(normalKey, randomCode, timeoutMinute);
		
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return resultEntity;
		}
		
		// ������֤�뵽�û��ֻ�
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
