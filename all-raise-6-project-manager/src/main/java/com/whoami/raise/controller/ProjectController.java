package com.whoami.raise.controller;

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
