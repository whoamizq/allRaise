package com.whoami.raise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.entity.po.MemberLaunchInfoPO;
import com.whoami.raise.entity.po.MemberLaunchInfoPOExample;
import com.whoami.raise.mapper.MemberLaunchInfoPOMapper;
import com.whoami.raise.service.MemberLaunchInfoService;
import com.whoami.raise.util.RaiseUtil;

@Service
@Transactional(readOnly = true)
public class MemberLaunchInfoServiceImpl implements MemberLaunchInfoService{

	@Autowired
	private MemberLaunchInfoPOMapper memberLaunchInfoMapper;

	/**
	 * 查询项目发起人信息
	 */
	@Override
	public MemberLaunchInfoPO getMemberLaunchInfoPO(String memberId) {
		MemberLaunchInfoPOExample example = new MemberLaunchInfoPOExample();
		example.createCriteria().andMemberidEqualTo(Integer.parseInt(memberId));
		List<MemberLaunchInfoPO> infoList = memberLaunchInfoMapper.selectByExample(example);
		if(RaiseUtil.collectionEffectiveCheck(infoList)) {
			return infoList.get(0);
		}else {
			return null;
		}
	}
}
