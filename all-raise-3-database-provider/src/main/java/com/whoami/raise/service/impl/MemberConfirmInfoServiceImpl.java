package com.whoami.raise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.mapper.MemberConfirmInfoPOMapper;
import com.whoami.raise.service.MemberConfirmInfoService;

@Service
@Transactional(readOnly = true)
public class MemberConfirmInfoServiceImpl implements MemberConfirmInfoService{

	@Autowired
	private MemberConfirmInfoPOMapper memberConfirmInfoMapper;
}
