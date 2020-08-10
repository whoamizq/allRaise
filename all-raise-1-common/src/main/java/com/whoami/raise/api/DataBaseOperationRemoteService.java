package com.whoami.raise.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.po.MemberPO;
import com.whoami.raise.entity.vo.ProjectVO;

@FeignClient("database-provider")
public interface DataBaseOperationRemoteService {
	// 查询用户是否存在
	@RequestMapping("/retrieve/loign/acct/count")
	ResultEntity<Integer> retrieveLoignAcctCount(@RequestParam("loginAcct") String loginAcct);
	
	// 保存用户
	@RequestMapping("/save/member/remote")
	ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);
	
	// 根据账号查询Member对象
	@RequestMapping("/retrieve/member/by/login/acct")
	ResultEntity<MemberPO> retrieveMemberByLoginAcct(@RequestParam("loginAcct") String loginAcct);

	// 数据库保存项目信息
	@RequestMapping(value = "/save/project/remote/{memberId}")
	ResultEntity<String> saveMemberRemote(@RequestBody ProjectVO projectVO, @PathVariable("memberId")String memberId);
}
