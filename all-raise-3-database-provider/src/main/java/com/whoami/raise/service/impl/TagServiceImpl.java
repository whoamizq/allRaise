package com.whoami.raise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.mapper.TagPOMapper;
import com.whoami.raise.service.TagService;

@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService{
	
	@Autowired
	private TagPOMapper tagPOMapper;

}
