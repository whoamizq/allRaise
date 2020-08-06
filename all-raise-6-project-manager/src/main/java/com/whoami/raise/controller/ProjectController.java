package com.whoami.raise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whoami.raise.api.DataBaseOperationRemoteService;
import com.whoami.raise.api.RedisOperationRemoteService;
import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.util.RaiseConstant;

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
	 * ��ʼ����Ŀ������Ƿ��¼
	 * @param memberSignToken
	 * @return
	 */
	@RequestMapping("/initCreation")
	public ResultEntity<String> initCreation(@RequestParam("memberSignToken") String memberSignToken){
		// ����Ƿ��¼,menberSignToken�Ƿ���Ч
		ResultEntity<String> resultEntity = redisOperationRemoteService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return resultEntity;
		}
		String memberId = resultEntity.getData();
		// ���¼���ڲ���
		if(memberId == null) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		return null;
	}
}
