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
	 * 登录
	 * @param memberVO
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/to/login.html")
	public String doLogin(MemberVO memberVO,Model model,HttpSession session) {
		// 调用远程方法执行登录操作
		ResultEntity<MemberSignSuccessVO> resultEntity = memberManagerRemoteService.login(memberVO.getLoginacct(), memberVO.getUserpswd());
		// 检查远程方法调用结果
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			model.addAttribute(RaiseConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
			return "member-login";
		}
		// 登录成功
		MemberSignSuccessVO memberSignSuccessVO = resultEntity.getData();
		// 将对象存入session域
		session.setAttribute(RaiseConstant.ATTR_NAME_LOGIN_ADMIN, memberSignSuccessVO);
		return "redirect:/member/to/member/center/page.html";
	}
}
