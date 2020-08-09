package com.whoami.raise.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
 * 初始化项目
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
	 * 项目信息保存
	 * @param projectVOFront 
	 * @return
	 */
	@RequestMapping(value = "/save/project/information")
	public ResultEntity<String> saveProjectInformation(@RequestBody ProjectVO projectVOFront){
		// 获取memberSignToken
		String memberSignToken = projectVOFront.getMemberSignToken();
		// 检查是否登录,menberSignToken是否有效
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		// 从projectVOFront中获取projectTempToken
		String projectTempToken = projectVOFront.getProjectTempToken();
		// project-manager工程访问Redis查询ProjectVO对象
		ResultEntity<String> resultEntityForGetValue = redisOperationRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if(ResultEntity.FAILED.equals(resultEntityForGetValue.getResult())) {
			return ResultEntity.failed(resultEntityForGetValue.getMessage());
		}
		// 从Redis中查询到JSON字符串
		String projectVOJSON = resultEntityForGetValue.getData();
		// 转换为projectVO对象
		ProjectVO projectVOBehind = JSON.parseObject(projectVOJSON,ProjectVO.class);
		projectVOFront.setHeaderPicturePath(projectVOBehind.getHeaderPicturePath());
		projectVOFront.setDetailPicturePathList(projectVOBehind.getDetailPicturePathList());
		// 将projectVOFront对象中的属性复制到projectVOBehind对象
		BeanUtils.copyProperties(projectVOFront, projectVOBehind);
		// 转换为JSON字符串
		String jsonString = JSON.toJSONString(projectVOBehind);
		// 将JSON字符串重新存入Redis
		
		return redisOperationRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}
	
	/**
	 * 保存图片--详情图片
	 * @param memberSignToken
	 * @param projectTempToken
	 * @param detailPicturePathList
	 * @return
	 */
	@RequestMapping(value = "/save/detailhead/picture/path/list")
	public ResultEntity<String> saveDetailPicturePathList(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTemoToken") String projectTempToken,
			@RequestParam("detailPicturePathList")List<String> detailPicturePathList){
		// 检查是否登录,menberSignToken是否有效
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		// project-manager工程访问Redis查询ProjectVO对象
		ResultEntity<String> resultEntityForGetValue = redisOperationRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if(ResultEntity.FAILED.equals(resultEntityForGetValue.getResult())) {
			return ResultEntity.failed(resultEntityForGetValue.getMessage());
		}
		// 从Redis查询到JSON字符串
		String projectVoJSON = resultEntityForGetValue.getData();
		// 将json字符串还原为projectVO对象
		ProjectVO projectVO = JSON.parseObject(projectVoJSON,ProjectVO.class);
		// 将图片路径存入ProjectVO对象
		projectVO.setDetailPicturePathList(detailPicturePathList);
		// 将projectvo对象转换为json字符串
		String jsonString = JSON.toJSONString(projectVO);
		// 将JSON字符串重新存入Redis
		
		return redisOperationRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}
	
	/**
	 * 保存图片--头图
	 * @param memberSignToken 会员token
	 * @param projectTempToken
	 * @param headerPicturePath 头图地址
	 * @return
	 */
	@RequestMapping(value = "/save/head/picture/path")
	public ResultEntity<String> saveHeadPicturePath(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTemoToken") String projectTempToken,
			@RequestParam("headPicturePath")String headerPicturePath){
		// 检查是否登录,menberSignToken是否有效
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		// project-manager工程访问Redis查询ProjectVO对象
		ResultEntity<String> resultEntityForGetValue = redisOperationRemoteService.retrieveStringValueByStringKey(projectTempToken);
		if(ResultEntity.FAILED.equals(resultEntityForGetValue.getResult())) {
			return ResultEntity.failed(resultEntityForGetValue.getMessage());
		}
		// 从Redis查询到JSON字符串
		String projectVoJSON = resultEntityForGetValue.getData();
		// 将json字符串还原为projectVO对象
		ProjectVO projectVO = JSON.parseObject(projectVoJSON,ProjectVO.class);
		// 将图片路径存入ProjectVO对象
		projectVO.setHeaderPicturePath(headerPicturePath);
		// 将projectvo对象转换为json字符串
		String jsonString = JSON.toJSONString(projectVO);
		// 将JSON字符串重新存入Redis
		
		return redisOperationRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
	}

	/**
	 * 初始化项目，检查是否登录
	 * @param memberSignToken
	 * @return
	 */
	@RequestMapping("/initCreation")
	public ResultEntity<ProjectVO> initCreation(@RequestParam("memberSignToken") String memberSignToken){
		// 检查是否登录,menberSignToken是否有效
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		String memberId = resultEntity.getData();
		// 请登录后再操作
		if(memberId == null) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// 创建projectVO对象
		ProjectVO projectVO = new ProjectVO();
		// 将memberSignToken存入ProjectVo对象
		projectVO.setMemberSignToken(memberSignToken);
		// 将peojectTempToken存入Project对象
		String projectTempToken = RaiseUtil.generateRedisKeyByPrefix(RaiseConstant.REDIS_PROJECT_TEMP_TOKEN_PREFIX);
		projectVO.setProjectTempToken(projectTempToken);
		// 将projectVo对象转换成JSON
		String jsonString = JSON.toJSONString(projectVO);
		// 存入Redis
		// 虽然是临时数据，但是不能指定一个固定的过期时间，在用户操作完成时删除
		redisOperationRemoteService.saveNormalStringKeyValue(projectTempToken, jsonString, -1);
		
		return ResultEntity.successWithData(projectVO);
	}
}
