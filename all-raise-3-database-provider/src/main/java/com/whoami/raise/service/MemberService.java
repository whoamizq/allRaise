package com.whoami.raise.service;

import com.whoami.raise.entity.po.MemberPO;

public interface MemberService {

	int getLoginAcctCount(String loginAcct);

	/**
	 * ����
	 * @param memberPO
	 */
	void saveMemberPO(MemberPO memberPO);

	/**
	 * ��ȡ��¼����
	 * @param loginAcct
	 * @return
	 */
	MemberPO getMemberByLoginAcct(String loginAcct);

}
