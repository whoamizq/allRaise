package com.whoami.raise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.whoami.raise.service.ProjectService;

/**
 * ��Ŀ������
 * @author whoami
 *
 */
@RestController
public class ProjectController {
	@Autowired
	private ProjectService projectService;
}
