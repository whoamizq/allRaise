package com.whoami.raise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.mapper.ReturnPOMapper;
import com.whoami.raise.service.ReturnService;

@Service
@Transactional(readOnly = true)
public class ReturnServiceImpl implements ReturnService{
	@Autowired
	private ReturnPOMapper returnPOMapper;
}
