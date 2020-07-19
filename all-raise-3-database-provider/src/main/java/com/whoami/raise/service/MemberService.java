package com.whoami.raise.service;

import com.whoami.raise.entity.po.MemberPO;

public interface MemberService {

	int getLoginAcctCount(String loginAcct);

	void saveMemberPO(MemberPO memberPO);

	MemberPO getMemberByLoginAcct(String loginAcct);

}
