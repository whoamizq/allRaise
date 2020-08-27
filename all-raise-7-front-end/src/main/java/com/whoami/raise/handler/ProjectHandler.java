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
 * ��Ŀ
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
	 * ִ��peoject��ʼ������
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/agree/protocol")
	public String agreeProtocol(HttpSession session,Model model) {
		// ��¼���
		MemberSignSuccessVO signVO = (MemberSignSuccessVO)session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		if(Objects.isNull(signVO)) {
			model.addAttribute(RaiseConstant.ATTR_NAME_MESSAGE,RaiseConstant.MESSAGE_ACCESS_DENIED);
			return "member-login";
		}
		// �ӵ�ǰsignVO�����л�ȡtoken,����Զ�̷�����ʼ��project
		ResultEntity<ProjectVO> resultEntity = projectOperationRemoteService.initCreation(signVO.getToken());
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		// �������������ʼ����Ŀ����Ϣ����session
		ProjectVO projectVO = resultEntity.getData();
		session.setAttribute(RaiseConstant.ATTR_NAME_INITED_PROJECT, projectVO);
		return "redirect:/project/to/create/project/page";
	}
	
	/**
	 * ��ҳ���ѯ��Ŀ��������Ϣ
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/to/create/project/page")
	public String toCreateProjectPage(HttpSession session,Model model) {
		// ��ȡ��ǰ��¼�û�
		MemberSignSuccessVO signVO = (MemberSignSuccessVO)session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		// ���sign����
		if(Objects.isNull(signVO)) {
			model.addAttribute(RaiseConstant.ATTR_NAME_MESSAGE,RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// ����token��ѯmemberLaunchInfo��Ϣ
		ResultEntity<MemberLaunchInfoPO> resultEntity = 
				memberManagerRemoteService.retrieveMemberLaunchInfoByMemberToken(signVO.getToken());
		// �����
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		// ��ȡ��ѯ���
		MemberLaunchInfoPO memberLaunchInfoPO = resultEntity.getData();
		// ����ģ��
		model.addAttribute("memberLaunchInfoPO", memberLaunchInfoPO);
		return "project-create";
	}
}
