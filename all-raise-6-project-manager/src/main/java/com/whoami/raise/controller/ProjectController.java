package com.whoami.raise.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.whoami.raise.api.DataBaseOperationRemoteService;
import com.whoami.raise.api.RedisOperationRemoteService;
import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.vo.MemberConfirmInfoVO;
import com.whoami.raise.entity.vo.ProjectVO;
import com.whoami.raise.entity.vo.ReturnVO;
import com.whoami.raise.util.RaiseConstant;
import com.whoami.raise.util.RaiseUtil;

/**
 * ��ʼ����Ŀ
 * @author whoami
 *
 */
@RestController
@RequestMapping(value = "project/manager")
public class ProjectController {
	@Autowired
	private RedisOperationRemoteService redisOperationRemoteService;
	
	@Autowired
	private DataBaseOperationRemoteService dataBaseOperationRemoteService;
	
	/**
	 * ���ݿⱣ�棬������Ŀ��Ϣ
	 * @param memberSignToken
	 * @param projectTempToken
	 * @return
	 */
	@RequestMapping(value = "/save/whole/project")
	public ResultEntity<String> saveWholeProject(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTempToken") String projectTempToken){
		// ����Ƿ��¼,menberSignToken�Ƿ���Ч
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		// ���¼���ٲ���
		String memberId = resultEntity.getData();
		if(StringUtils.isEmpty(memberId)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// project-manager���̷���Redis��ѯProjectVO����
		ResultEntity<String> resultEntityForGetValue = redisOperationRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if(ResultEntity.FAILED.equals(resultEntityForGetValue.getResult())) {
			return ResultEntity.failed(resultEntityForGetValue.getMessage());
		}
		// ��Redis��ѯ��JSON�ַ���
		String projectVOJSON = resultEntityForGetValue.getData();
		// ת��ΪprojectVO����
		ProjectVO projectVO = JSON.parseObject(projectVOJSON,ProjectVO.class);
		// ִ�б���
		ResultEntity<String> resultEntityForSave = dataBaseOperationRemoteService.saveMemberRemote(projectVO,memberId);
		if(ResultEntity.FAILED.equals(resultEntityForSave.getResult())) {
			return resultEntityForSave;
		}
		// ɾ��Redis����ʱ����
		return redisOperationRemoteService.removeByKey(projectTempToken);
	}
	
	/**
	 * ����ȷ����Ϣ
	 * @param memberConfirmInfoVO
	 * @return
	 */
	@RequestMapping(value = "/save/confirm/information")
	public ResultEntity<String> saveConfirmInformation(@RequestBody MemberConfirmInfoVO memberConfirmInfoVO){
		// ��ȡmemberSignToken
		String memberSignToken = memberConfirmInfoVO.getMemberSignToken();
		// ����Ƿ��¼,menberSignToken�Ƿ���Ч
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		// ��projectVOFront�л�ȡprojectTempToken
		String projectTempToken = memberConfirmInfoVO.getProjectTempToken();
		// project-manager���̷���Redis��ѯProjectVO����
		ResultEntity<String> resultEntityForGetValue = redisOperationRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if(ResultEntity.FAILED.equals(resultEntityForGetValue.getResult())) {
			return ResultEntity.failed(resultEntityForGetValue.getMessage());
		}
		// ��Redis�в�ѯ��JSON�ַ���
		String projectVOJSON = resultEntityForGetValue.getData();
		// ת��ΪprojectVO����
		ProjectVO projectVOBehind = JSON.parseObject(projectVOJSON,ProjectVO.class);
		projectVOBehind.setMemberConfirmInfoVO(memberConfirmInfoVO);
		// ���¶�projectvo�������JSONת��
		String jsonString = JSON.toJSONString(projectVOBehind);
		// ���°�ProjectVo���󱣴�Redis
		return redisOperationRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}
	
	/**
	 * ����ر���Ϣ
	 * @param returnVO
	 * @return
	 */
	@RequestMapping(value = "/save/return/information")
	public ResultEntity<String> saveReturnInformation(@RequestBody ReturnVO returnVO){
		// ��ȡmemberSignToken
		String memberSignToken = returnVO.getMemberSignToken();
		// ����Ƿ��¼,menberSignToken�Ƿ���Ч
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		// ��projectVOFront�л�ȡprojectTempToken
		String projectTempToken = returnVO.getProjectTempToken();
		// project-manager���̷���Redis��ѯProjectVO����
		ResultEntity<String> resultEntityForGetValue = redisOperationRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if(ResultEntity.FAILED.equals(resultEntityForGetValue.getResult())) {
			return ResultEntity.failed(resultEntityForGetValue.getMessage());
		}
		// ��Redis�в�ѯ��JSON�ַ���
		String projectVOJSON = resultEntityForGetValue.getData();
		// ת��ΪprojectVO����
		ProjectVO projectVOBehind = JSON.parseObject(projectVOJSON,ProjectVO.class);
		// ��ȡ�ɵĻر���Ϣ����
		List<ReturnVO> returnVOList = projectVOBehind.getReturnVOList();
		// �ж�lsit�Ƿ�������
		if(!RaiseUtil.collectionEffectiveCheck(returnVOList)) {
			// ��ʼ��
			returnVOList = new ArrayList<>();
			projectVOBehind.setReturnVOList(returnVOList);
		}
		// ����ǰ�ر���Ϣ����List
		returnVOList.add(returnVO);
		// ���¶�projectvo�������JSONת��
		String jsonString = JSON.toJSONString(projectVOBehind);
		// ���°�ProjectVo���󱣴�Redis
		
		return redisOperationRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}
	
	/**
	 * ��Ŀ��Ϣ����
	 * @param projectVOFront 
	 * @return
	 */
	@RequestMapping(value = "/save/project/information")
	public ResultEntity<String> saveProjectInformation(@RequestBody ProjectVO projectVOFront){
		// ��ȡmemberSignToken
		String memberSignToken = projectVOFront.getMemberSignToken();
		// ����Ƿ��¼,menberSignToken�Ƿ���Ч
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		// ��projectVOFront�л�ȡprojectTempToken
		String projectTempToken = projectVOFront.getProjectTempToken();
		// project-manager���̷���Redis��ѯProjectVO����
		ResultEntity<String> resultEntityForGetValue = redisOperationRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if(ResultEntity.FAILED.equals(resultEntityForGetValue.getResult())) {
			return ResultEntity.failed(resultEntityForGetValue.getMessage());
		}
		// ��Redis�в�ѯ��JSON�ַ���
		String projectVOJSON = resultEntityForGetValue.getData();
		// ת��ΪprojectVO����
		ProjectVO projectVOBehind = JSON.parseObject(projectVOJSON,ProjectVO.class);
		projectVOFront.setHeaderPicturePath(projectVOBehind.getHeaderPicturePath());
		projectVOFront.setDetailPicturePathList(projectVOBehind.getDetailPicturePathList());
		// ��projectVOFront�����е����Ը��Ƶ�projectVOBehind����
		BeanUtils.copyProperties(projectVOFront, projectVOBehind);
		// ת��ΪJSON�ַ���
		String jsonString = JSON.toJSONString(projectVOBehind);
		// ��JSON�ַ������´���Redis
		
		return redisOperationRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}
	
	/**
	 * ����ͼƬ--����ͼƬ
	 * @param memberSignToken
	 * @param projectTempToken
	 * @param detailPicturePathList
	 * @return
	 */
	@RequestMapping(value = "/save/detail/picture/path/list")
	public ResultEntity<String> saveDetailPicturePathList(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTemoToken") String projectTempToken,
			@RequestParam("detailPicturePathList")List<String> detailPicturePathList){
		// ����Ƿ��¼,menberSignToken�Ƿ���Ч
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		// project-manager���̷���Redis��ѯProjectVO����
		ResultEntity<String> resultEntityForGetValue = redisOperationRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if(ResultEntity.FAILED.equals(resultEntityForGetValue.getResult())) {
			return ResultEntity.failed(resultEntityForGetValue.getMessage());
		}
		// ��Redis��ѯ��JSON�ַ���
		String projectVoJSON = resultEntityForGetValue.getData();
		// ��json�ַ�����ԭΪprojectVO����
		ProjectVO projectVO = JSON.parseObject(projectVoJSON,ProjectVO.class);
		// ��ͼƬ·������ProjectVO����
		projectVO.setDetailPicturePathList(detailPicturePathList);
		// ��projectvo����ת��Ϊjson�ַ���
		String jsonString = JSON.toJSONString(projectVO);
		// ��JSON�ַ������´���Redis
		
		return redisOperationRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}
	
	/**
	 * ����ͼƬ--ͷͼ
	 * @param memberSignToken ��Աtoken
	 * @param projectTempToken
	 * @param headerPicturePath ͷͼ��ַ
	 * @return
	 */
	@RequestMapping(value = "/save/head/picture/path")
	public ResultEntity<String> saveHeadPicturePath(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTemoToken") String projectTempToken,
			@RequestParam("headPicturePath")String headerPicturePath){
		// ����Ƿ��¼,menberSignToken�Ƿ���Ч
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		// project-manager���̷���Redis��ѯProjectVO����
		ResultEntity<String> resultEntityForGetValue = redisOperationRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if(ResultEntity.FAILED.equals(resultEntityForGetValue.getResult())) {
			return ResultEntity.failed(resultEntityForGetValue.getMessage());
		}
		// ��Redis��ѯ��JSON�ַ���
		String projectVoJSON = resultEntityForGetValue.getData();
		// ��json�ַ�����ԭΪprojectVO����
		ProjectVO projectVO = JSON.parseObject(projectVoJSON,ProjectVO.class);
		// ��ͼƬ·������ProjectVO����
		projectVO.setHeaderPicturePath(headerPicturePath);
		// ��projectvo����ת��Ϊjson�ַ���
		String jsonString = JSON.toJSONString(projectVO);
		// ��JSON�ַ������´���Redis
		
		return redisOperationRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}

	/**
	 * ��ʼ����Ŀ������Ƿ��¼
	 * @param memberSignToken
	 * @return
	 */
	@RequestMapping("/initCreation")
	public ResultEntity<ProjectVO> initCreation(@RequestParam("memberSignToken") String memberSignToken){
		// ����Ƿ��¼,menberSignToken�Ƿ���Ч
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		String memberId = resultEntity.getData();
		// ���¼���ٲ���
		if(memberId == null) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// ����projectVO����
		ProjectVO projectVO = new ProjectVO();
		// ��memberSignToken����ProjectVo����
		projectVO.setMemberSignToken(memberSignToken);
		// ��peojectTempToken����Project����
		String projectTempToken = RaiseUtil.generateRedisKeyByPrefix(RaiseConstant.REDIS_PROJECT_TEMP_TOKEN_PREFIX);
		projectVO.setProjectTempToken(projectTempToken);
		// ��projectVo����ת����JSON
		String jsonString = JSON.toJSONString(projectVO);
		// ����Redis
		// ��Ȼ����ʱ���ݣ����ǲ���ָ��һ���̶��Ĺ���ʱ�䣬���û��������ʱɾ��
		redisOperationRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
		
		return ResultEntity.successWithData(projectVO);
	}
}
