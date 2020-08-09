package com.whoami.raise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.whoami.raise.api.DataBaseOperationRemoteService;
import com.whoami.raise.api.RedisOperationRemoteService;
import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.vo.ProjectVO;
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
	 * ����ͼƬ--����ͼƬ
	 * @param memberSignToken
	 * @param projectTempToken
	 * @param detailPicturePathList
	 * @return
	 */
	@RequestMapping(value = "/save/head/picture/path/list")
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
