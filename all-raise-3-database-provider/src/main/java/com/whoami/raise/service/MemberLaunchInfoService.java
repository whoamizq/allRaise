package com.whoami.raise.service;

import com.whoami.raise.entity.po.MemberLaunchInfoPO;

public interface MemberLaunchInfoService {

	/**
	 * ��ѯ��Ŀ��������Ϣ
	 * @param memberId
	 * @return
	 */
	MemberLaunchInfoPO getMemberLaunchInfoPO(String memberId);

}
