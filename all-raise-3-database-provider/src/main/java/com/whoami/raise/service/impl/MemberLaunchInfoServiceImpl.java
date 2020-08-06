package com.whoami.raise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.mapper.MemberLaunchInfoPOMapper;
import com.whoami.raise.service.MemberLaunchInfoService;

@Service
@Transactional(readOnly = true)
public class MemberLaunchInfoServiceImpl implements MemberLaunchInfoService{

	@Autowired
	private MemberLaunchInfoPOMapper memberLaunchInfoMapper;
}
