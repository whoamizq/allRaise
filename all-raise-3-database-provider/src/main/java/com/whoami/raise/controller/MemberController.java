package com.whoami.raise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.po.MemberLaunchInfoPO;
import com.whoami.raise.entity.po.MemberPO;
import com.whoami.raise.service.MemberLaunchInfoService;
import com.whoami.raise.service.MemberService;
import com.whoami.raise.util.RaiseConstant;
import com.whoami.raise.util.RaiseUtil;

@RestController
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberLaunchInfoService memberLaunchInfoService;
	
	/**
	 * ��ѯ��Ŀ��������Ϣ
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "retrieve/member/launch/info/po")
	public ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoPO(@RequestParam("memberId")String memberId){
		MemberLaunchInfoPO memberLaunchInfoPO = memberLaunchInfoService.getMemberLaunchInfoPO(memberId);
		return ResultEntity.successWithData(memberLaunchInfoPO);
	}
	/**
	 * �����˺Ų�ѯmenber����
	 * @param loginAcct
	 * @return
	 */
	@RequestMapping("/retrieve/member/by/login/acct")
	public ResultEntity<MemberPO> retrieveMemberByLoginAcct(
			@RequestParam("loginAcct") String loginAcct){
		try {
			MemberPO memberPO = memberService.getMemberByLoginAcct(loginAcct);
			return ResultEntity.successWithData(memberPO);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	// ��ѯ�û��Ƿ����
	@RequestMapping("/retrieve/loign/acct/count")
	ResultEntity<Integer> retrieveLoignAcctCount(
			@RequestParam("loginAcct") String loginAcct){
		if(!RaiseUtil.strEffectiveCheck(loginAcct)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_LOGINACCT_INVALID);
		}
		try {
			int count = memberService.getLoginAcctCount(loginAcct);
			
			return ResultEntity.successWithData(count);
		}catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
	
	// �����û�
	@RequestMapping("/save/member/remote")
	public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO) {
		try {
			// ִ�б���
			memberService.saveMemberPO(memberPO);
			
		}catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
		return ResultEntity.successNoData();
	}
}
