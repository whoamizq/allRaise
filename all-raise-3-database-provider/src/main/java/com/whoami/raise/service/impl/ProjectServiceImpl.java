package com.whoami.raise.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.entity.po.ProjectPO;
import com.whoami.raise.entity.vo.ProjectVO;
import com.whoami.raise.mapper.ProjectPOMapper;
import com.whoami.raise.mapper.TagPOMapper;
import com.whoami.raise.mapper.TypePOMapper;
import com.whoami.raise.service.ProjectService;
import com.whoami.raise.util.RaiseConstant;
import com.whoami.raise.util.RaiseUtil;

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
	@Autowired
	private TypePOMapper typePOMapper;
	@Autowired
	private TagPOMapper tagPOMapper;

	
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveProject(ProjectVO projectVO, String memberId) {
		// TODO Auto-generated method stub
		// 保存projectVO
		ProjectPO projectPO = new ProjectPO();
		BeanUtils.copyProperties(projectVO, projectPO);
		projectMapper.insert(projectPO);
		
		// 获取保存ProjectPO后得到的自增主键
		// 在ProjectPOMapper.xml文件中insert方法对应的标签中设置useGeneratedKeys="true" keyProperty="id"
		Integer projectId = projectPO.getId();
		
		// 保存typeIdList
		List<Integer> typeIdList = projectVO.getTypeIdList();
		if(RaiseUtil.collectionEffectiveCheck(typeIdList)) {
			typePOMapper.insertRelationshipBatch(projectId,typeIdList);
		}
		
		// 保存tagIdList
		List<Integer> tagIdList = projectVO.getTagIdList();
		if(RaiseUtil.collectionEffectiveCheck(tagIdList)) {
			tagPOMapper.insertRelationshipBatch(projectId, tagIdList);
		}
		
		// 保存detailPicturePathList
		
		// 保存MemberLaunchInfoPO
		
		// 根据ReturnVO的List保存ReturnPO
		
		// 保存MemberConfirmInfoPO
		
	}

}
