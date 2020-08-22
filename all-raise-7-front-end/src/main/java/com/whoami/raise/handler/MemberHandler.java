package com.whoami.raise.handler;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whoami.raise.api.MemberManagerRemoteService;
import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.vo.MemberSignSuccessVO;
import com.whoami.raise.entity.vo.MemberVO;
import com.whoami.raise.util.RaiseConstant;

@Controller(value = "/member")
public class MemberHandler {
	@Autowired
	private MemberManagerRemoteService memberManagerRemoteService;
	
	/**
	 * ��¼
	 * @param memberVO
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/to/login.html")
	public String doLogin(MemberVO memberVO,Model model,HttpSession session) {
		// ����Զ�̷���ִ�е�¼����
		ResultEntity<MemberSignSuccessVO> resultEntity = memberManagerRemoteService.login(memberVO.getLoginacct(), memberVO.getUserpswd());
		// ���Զ�̷������ý��
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			model.addAttribute(RaiseConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
			return "member-login";
		}
		// ��¼�ɹ�
		MemberSignSuccessVO memberSignSuccessVO = resultEntity.getData();
		// ���������session��
		session.setAttribute(RaiseConstant.ATTR_NAME_LOGIN_ADMIN, memberSignSuccessVO);
		return "redirect:/member/to/member/center/page.html";
	}
}
