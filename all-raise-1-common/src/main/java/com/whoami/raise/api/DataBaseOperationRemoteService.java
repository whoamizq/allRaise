package com.whoami.raise.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.po.MemberPO;

@FeignClient("database-provider")
public interface DataBaseOperationRemoteService {
	// ��ѯ�û��Ƿ����
	@RequestMapping("/retrieve/loign/acct/count")
	ResultEntity<Integer> retrieveLoignAcctCount(@RequestParam("loginAcct") String loginAcct);
	
	// �����û�
	@RequestMapping("/save/member/remote")
	ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);
	
	// �����˺Ų�ѯMember����
	@RequestMapping("/retrieve/member/by/login/acct")
	ResultEntity<MemberPO> retrieveMemberByLoginAcct(@RequestParam("loginAcct") String loginAcct);
}
