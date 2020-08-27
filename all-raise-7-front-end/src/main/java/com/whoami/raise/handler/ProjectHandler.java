package com.whoami.raise.handler;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whoami.raise.api.MemberManagerRemoteService;
import com.whoami.raise.api.ProjectOperationRemoteService;
import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.po.MemberLaunchInfoPO;
import com.whoami.raise.entity.vo.MemberLauchInfoVO;
import com.whoami.raise.entity.vo.MemberSignSuccessVO;
import com.whoami.raise.entity.vo.ProjectVO;
import com.whoami.raise.util.RaiseConstant;

/**
 * 项目
 * @author whoami
 *
 */
@Controller(value = "/project")
public class ProjectHandler {
	@Autowired
	private ProjectOperationRemoteService projectOperationRemoteService;
	@Autowired
	private MemberManagerRemoteService memberManagerRemoteService;
	
	/**
	 * 执行peoject初始化操作
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/agree/protocol")
	public String agreeProtocol(HttpSession session,Model model) {
		// 登录检查
		MemberSignSuccessVO signVO = (MemberSignSuccessVO)session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		if(Objects.isNull(signVO)) {
			model.addAttribute(RaiseConstant.ATTR_NAME_MESSAGE,RaiseConstant.MESSAGE_ACCESS_DENIED);
			return "member-login";
		}
		// 从当前signVO对象中获取token,调用远程方法初始化project
		ResultEntity<ProjectVO> resultEntity = projectOperationRemoteService.initCreation(signVO.getToken());
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		// 补充操作：将初始化项目的信息存入session
		ProjectVO projectVO = resultEntity.getData();
		session.setAttribute(RaiseConstant.ATTR_NAME_INITED_PROJECT, projectVO);
		return "redirect:/project/to/create/project/page";
	}
	
	/**
	 * 在页面查询项目发起人信息
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/to/create/project/page")
	public String toCreateProjectPage(HttpSession session,Model model) {
		// 获取当前登录用户
		MemberSignSuccessVO signVO = (MemberSignSuccessVO)session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		// 检查sign参数
		if(Objects.isNull(signVO)) {
			model.addAttribute(RaiseConstant.ATTR_NAME_MESSAGE,RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// 根据token查询memberLaunchInfo信息
		ResultEntity<MemberLaunchInfoPO> resultEntity = 
				memberManagerRemoteService.retrieveMemberLaunchInfoByMemberToken(signVO.getToken());
		// 检查结果
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		// 获取查询结果
		MemberLaunchInfoPO memberLaunchInfoPO = resultEntity.getData();
		// 存入模型
		model.addAttribute("memberLaunchInfoPO", memberLaunchInfoPO);
		return "project-create";
	}
}
