package com.whoami.raise.handler;

import java.util.Objects;

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
	 * 退出登录
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout.html")
	public String logout(HttpSession session) {
		// 从现有Session中获取已登录的Member
		MemberSignSuccessVO memberSignSuccessVO = (MemberSignSuccessVO)session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		// 校验数据,是否已经退出
		if(Objects.isNull(memberSignSuccessVO)) {
			return "redirect:/";
		}
		// 调用远程方法删除Redis中存储的token
		ResultEntity<String> resultEntity = memberManagerRemoteService.logout(memberSignSuccessVO.getToken());
		// 调用是否成功
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		// 释放单当前session
		session.invalidate();
		return "redirect:/index.html";
	}
	
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
		session.setAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER, memberSignSuccessVO);
		return "redirect:/member/to/member/center/page.html";
	}
}
