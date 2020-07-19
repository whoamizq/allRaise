package com.whoami.raise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.entity.po.MemberPO;
import com.whoami.raise.entity.po.MemberPOExample;
import com.whoami.raise.mapper.MemberPOMapper;
import com.whoami.raise.service.MemberService;
import com.whoami.raise.util.RaiseUtil;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberPOMapper memberPOMapper;
	
	/**
	 * ���ز�ѯ�û�����
	 */
	@Override
	public int getLoginAcctCount(String loginAcct) {
		MemberPOExample example = new MemberPOExample();
		example.createCriteria().andLoginacctEqualTo(loginAcct);
		return memberPOMapper.countByExample(example);
	}
	
	/**
	 * �����û�
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveMemberPO(MemberPO memberPO) {
		memberPOMapper.insert(memberPO);
	}

	/**
	 * �����˺Ų�ѯmember����
	 */
	@Override
	public MemberPO getMemberByLoginAcct(String loginAcct) {
		MemberPOExample example = new MemberPOExample();
		example.createCriteria().andLoginacctEqualTo(loginAcct);
		List<MemberPO> list = memberPOMapper.selectByExample(example);
		
		if(RaiseUtil.collectionEffectiveCheck(list)) {
			return list.get(0);
		}
		return null;
	}

}
