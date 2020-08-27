package com.whoami.raise.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.po.MemberLaunchInfoPO;
import com.whoami.raise.entity.vo.MemberSignSuccessVO;
import com.whoami.raise.entity.vo.MemberVO;

/**
 * ��ȡ�ӿ�
 * @author whoami
 *
 */
@FeignClient("member-manager")
public interface MemberManagerRemoteService {
	/**
	 * ��ѯ��Ŀ��������Ϣ-��member-manager��ѯ
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/retrieve/member/launch/info/by/member/token")
	public ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoByMemberToken(@RequestParam("token") String token);

	/**
	 * �˳���¼
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/member/manager/logout")
	public ResultEntity<String> logout(@RequestParam("token") String token);
	
	/**
	 * ��¼
	 * @param loginAcct
	 * @param userPswd
	 * @return
	 */
	@RequestMapping(value = "/member/manager/login")
	public ResultEntity<MemberSignSuccessVO> login(
			@RequestParam("loginAcct") String loginAcct,
			@RequestParam("userPswd") String userPswd);
	
	/**
	 * ע��
	 * @param memberVO
	 * @return
	 */
	@RequestMapping(value = "/member/manager/register")
	public ResultEntity<String> register(@RequestParam MemberVO memberVO);
	
	/**
	 * ���Ͷ�����֤��
	 * @param phoneNum
	 * @return
	 */
	@RequestMapping(value = "/member/manager/send/code")
	public ResultEntity<String> sendCode(@RequestParam("phoneNum") String phoneNum);
}
