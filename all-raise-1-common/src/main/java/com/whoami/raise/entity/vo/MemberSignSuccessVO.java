package com.whoami.raise.entity.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 封装登录vo对象
 * @author whoami
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignSuccessVO implements Serializable{
	/*
	 * 用户名
	 */
	private String username;
	/*
	 * 邮箱
	 */
	private String email;
	/*
	 * 令牌
	 */
	private String token;
}
