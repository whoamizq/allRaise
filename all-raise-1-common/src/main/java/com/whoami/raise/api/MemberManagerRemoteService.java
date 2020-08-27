package com.whoami.raise.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.po.MemberLaunchInfoPO;
import com.whoami.raise.entity.vo.MemberSignSuccessVO;
import com.whoami.raise.entity.vo.MemberVO;

/**
 * 抽取接口
 * @author whoami
 *
 */
@FeignClient("member-manager")
public interface MemberManagerRemoteService {
	/**
	 * 查询项目发起人信息-在member-manager查询
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/retrieve/member/launch/info/by/member/token")
	public ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoByMemberToken(@RequestParam("token") String token);

	/**
	 * 退出登录
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/member/manager/logout")
	public ResultEntity<String> logout(@RequestParam("token") String token);
	
	/**
	 * 登录
	 * @param loginAcct
	 * @param userPswd
	 * @return
	 */
	@RequestMapping(value = "/member/manager/login")
	public ResultEntity<MemberSignSuccessVO> login(
			@RequestParam("loginAcct") String loginAcct,
			@RequestParam("userPswd") String userPswd);
	
	/**
	 * 注册
	 * @param memberVO
	 * @return
	 */
	@RequestMapping(value = "/member/manager/register")
	public ResultEntity<String> register(@RequestParam MemberVO memberVO);
	
	/**
	 * 发送短信验证码
	 * @param phoneNum
	 * @return
	 */
	@RequestMapping(value = "/member/manager/send/code")
	public ResultEntity<String> sendCode(@RequestParam("phoneNum") String phoneNum);
}
