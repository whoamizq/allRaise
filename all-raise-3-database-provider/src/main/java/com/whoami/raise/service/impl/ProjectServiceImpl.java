package com.whoami.raise.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.service.ProjectService;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService{

}
