package com.whoami.raise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.mapper.ProjectItemPicPOMapper;
import com.whoami.raise.service.ProjectItemPicService;

@Service
@Transactional(readOnly = true)
public class ProjectItemPicServiceImpl implements ProjectItemPicService{
	
	@Autowired
	private ProjectItemPicPOMapper projectItemPicMapper;

}
