package com.whoami.raise.service;

import com.whoami.raise.entity.vo.ProjectVO;

public interface ProjectService {

	/**
	 * ���ݿⱣ��--����projectPO
	 * @param projectVO
	 * @param memberId
	 */
	void saveProject(ProjectVO projectVO, String memberId);

}
