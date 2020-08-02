package com.whoami.raise.service;

import com.whoami.raise.entity.po.MemberPO;

public interface MemberService {

	int getLoginAcctCount(String loginAcct);

	/**
	 * 保存
	 * @param memberPO
	 */
	void saveMemberPO(MemberPO memberPO);

	/**
	 * 获取登录对象
	 * @param loginAcct
	 * @return
	 */
	MemberPO getMemberByLoginAcct(String loginAcct);

}
