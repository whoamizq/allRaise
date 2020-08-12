package com.whoami.raise.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.entity.po.ProjectItemPicPO;
import com.whoami.raise.entity.po.ProjectPO;
import com.whoami.raise.entity.vo.ProjectVO;
import com.whoami.raise.mapper.ProjectItemPicPOMapper;
import com.whoami.raise.mapper.ProjectPOMapper;
import com.whoami.raise.mapper.TagPOMapper;
import com.whoami.raise.mapper.TypePOMapper;
import com.whoami.raise.service.ProjectService;
import com.whoami.raise.util.RaiseConstant;
import com.whoami.raise.util.RaiseUtil;

/**
 * ��Ŀ��ʵ����
 * @author whoami
 *
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectPOMapper projectMapper;
	@Autowired
	private TypePOMapper typePOMapper;
	@Autowired
	private TagPOMapper tagPOMapper;
	@Autowired
	private ProjectItemPicPOMapper projectItemPicPOMapper;

	
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveProject(ProjectVO projectVO, String memberId) {
		// TODO Auto-generated method stub
		// ����projectVO
		ProjectPO projectPO = new ProjectPO();
		BeanUtils.copyProperties(projectVO, projectPO);
		projectMapper.insert(projectPO);
		
		// ��ȡ����ProjectPO��õ�����������
		// ��ProjectPOMapper.xml�ļ���insert������Ӧ�ı�ǩ������useGeneratedKeys="true" keyProperty="id"
		Integer projectId = projectPO.getId();
		
		// ����typeIdList
		List<Integer> typeIdList = projectVO.getTypeIdList();
		if(RaiseUtil.collectionEffectiveCheck(typeIdList)) {
			typePOMapper.insertRelationshipBatch(projectId,typeIdList);
		}
		
		// ����tagIdList
		List<Integer> tagIdList = projectVO.getTagIdList();
		if(RaiseUtil.collectionEffectiveCheck(tagIdList)) {
			tagPOMapper.insertRelationshipBatch(projectId, tagIdList);
		}
		
		// ����detailPicturePathList
		// ��VO�����л�ȡdetailPicturePathList
		List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
		if(RaiseUtil.collectionEffectiveCheck(detailPicturePathList)) {
			// ����һ����List���ϣ������洢ProjectItemPicPO����
			List<ProjectItemPicPO> projectItemPicPOList = new ArrayList<ProjectItemPicPO>();
			//����detailPicturePathList
			for (String detailPath : detailPicturePathList) {
				// ����projectItemPicPO����
				ProjectItemPicPO projectItemPicPO = new ProjectItemPicPO(null,projectId,detailPath);
				projectItemPicPOList.add(projectItemPicPO);
			}
			// ִ����������
			projectItemPicPOMapper.insertBatch(projectItemPicPOList);
		}
		
		// ����MemberLaunchInfoPO
		
		// ����ReturnVO��List����ReturnPO
		
		// ����MemberConfirmInfoPO
		
	}

}
