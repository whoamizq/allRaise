package com.whoami.raise.service;

import com.whoami.raise.entity.po.MemberLaunchInfoPO;

public interface MemberLaunchInfoService {

	/**
	 * 查询项目发起人信息
	 * @param memberId
	 * @return
	 */
	MemberLaunchInfoPO getMemberLaunchInfoPO(String memberId);

}
