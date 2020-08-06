package com.whoami.raise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.mapper.ProjectPOMapper;
import com.whoami.raise.service.ProjectService;

/**
 * 项目表实现类
 * @author whoami
 *
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectPOMapper projectMapper;

}
