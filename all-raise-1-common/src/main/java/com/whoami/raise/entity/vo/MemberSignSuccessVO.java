package com.whoami.raise.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * ��װ��¼vo����
 * @author whoami
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignSuccessVO {
	/*
	 * �û���
	 */
	private String username;
	/*
	 * ����
	 */
	private String email;
	/*
	 * ����
	 */
	private String token;
}
