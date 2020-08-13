package com.whoami.raise.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.whoami.raise.entity.po.MemberConfirmInfoPO;
import com.whoami.raise.entity.po.MemberLaunchInfoPO;
import com.whoami.raise.entity.po.ProjectItemPicPO;
import com.whoami.raise.entity.po.ProjectPO;
import com.whoami.raise.entity.po.ReturnPO;
import com.whoami.raise.entity.vo.MemberConfirmInfoVO;
import com.whoami.raise.entity.vo.MemberLauchInfoVO;
import com.whoami.raise.entity.vo.ProjectVO;
import com.whoami.raise.entity.vo.ReturnVO;
import com.whoami.raise.mapper.MemberConfirmInfoPOMapper;
import com.whoami.raise.mapper.MemberLaunchInfoPOMapper;
import com.whoami.raise.mapper.ProjectItemPicPOMapper;
import com.whoami.raise.mapper.ProjectPOMapper;
import com.whoami.raise.mapper.ReturnPOMapper;
import com.whoami.raise.mapper.TagPOMapper;
import com.whoami.raise.mapper.TypePOMapper;
import com.whoami.raise.service.ProjectService;
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
	@Autowired
	private ProjectItemPicPOMapper projectItemPicPOMapper;
	@Autowired
	private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
	@Autowired
	private ReturnPOMapper returnPOMapper;
	@Autowired
	private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

	
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveProject(ProjectVO projectVO, String memberId) {
		// TODO Auto-generated method stub
		// 保存projectVO
		ProjectPO projectPO = new ProjectPO();
		BeanUtils.copyProperties(projectVO, projectPO);
		projectPO.setMemberid(Integer.parseInt(memberId));
		projectMapper.insert(projectPO);
		
		// 获取保存ProjectPO后得到的自增主键
		// 在ProjectPOMapper.xml文件中insert方法对应的标签中设置useGeneratedKeys="true" keyProperty="id"
		Integer projectId = projectPO.getId();
		
		// 保存typeIdList
		List<Integer> typeIdList = projectVO.getTypeIdList();
		if(RaiseUtil.collectionEffectiveCheck(typeIdList)) { // 是否存在
			typePOMapper.insertRelationshipBatch(projectId,typeIdList);
		}
		
		// 保存tagIdList
		List<Integer> tagIdList = projectVO.getTagIdList();
		if(RaiseUtil.collectionEffectiveCheck(tagIdList)) {
			tagPOMapper.insertRelationshipBatch(projectId, tagIdList);
		}
		
		// 保存detailPicturePathList
		// 从VO对象中获取detailPicturePathList
		List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
		if(RaiseUtil.collectionEffectiveCheck(detailPicturePathList)) {
			// 创建一个空List集合，用来存储ProjectItemPicPO对象
			List<ProjectItemPicPO> projectItemPicPOList = new ArrayList<ProjectItemPicPO>();
			//遍历detailPicturePathList
			for (String detailPath : detailPicturePathList) {
				// 创建projectItemPicPO对象
				ProjectItemPicPO projectItemPicPO = new ProjectItemPicPO(null,projectId,detailPath);
				projectItemPicPOList.add(projectItemPicPO);
			}
			// 执行批量保存
			projectItemPicPOMapper.insertBatch(projectItemPicPOList);
		}
		
		// 保存MemberLaunchInfoPO
		MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
		if(memberLauchInfoVO!=null) { // 不为空
			MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
			BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
			memberLaunchInfoPO.setMemberid(Integer.parseInt(memberId));
			memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
		}
		
		// 根据ReturnVO的List保存ReturnPO
		List<ReturnVO> returnVOList = projectVO.getReturnVOList();
		if(RaiseUtil.collectionEffectiveCheck(returnVOList)) { // 数组不能为空
			List<ReturnPO> returnPOList = new ArrayList<>();
			
			for(ReturnVO returnVO:returnVOList) {
				ReturnPO returnPO = new ReturnPO();
				BeanUtils.copyProperties(returnVO, returnPO);
				returnPO.setProjectid(projectId);
				returnPOList.add(returnPO);
			}
			returnPOMapper.insertBatch(returnPOList);
		}
		
		// 保存MemberConfirmInfoPO
		MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
		if(memberConfirmInfoVO!=null) {
			MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO(null,
					Integer.parseInt(memberId),memberConfirmInfoVO.getPaynum(),
					memberConfirmInfoVO.getCardnum());
			memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
		}
		
	}

}
