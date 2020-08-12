package com.whoami.raise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.vo.ProjectVO;
import com.whoami.raise.service.ProjectService;

/**
 * 项目控制类
 * @author whoami
 *
 */
@RestController
@RequestMapping(value = "/save/project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	/**
	 * 数据库保存--保存projectPO
	 * @param projectVO
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/remote/{memberId}")
	public ResultEntity<String> saveProjectRemote(
			@RequestBody ProjectVO projectVO,
			@PathVariable("memberId")String memberId){
		try {
			projectService.saveProject(projectVO,memberId);
			return ResultEntity.successNoData();
		}catch (Exception e) {
			// TODO: handle exception
			return ResultEntity.failed(e.getMessage());
		}
		
	}
}
