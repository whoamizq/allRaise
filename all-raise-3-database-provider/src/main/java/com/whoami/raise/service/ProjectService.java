package com.whoami.raise.service;

import com.whoami.raise.entity.vo.ProjectVO;

public interface ProjectService {

	/**
	 * 数据库保存--保存projectPO
	 * @param projectVO
	 * @param memberId
	 */
	void saveProject(ProjectVO projectVO, String memberId);

}
