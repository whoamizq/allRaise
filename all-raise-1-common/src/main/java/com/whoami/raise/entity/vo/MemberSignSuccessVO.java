package com.whoami.raise.entity.vo;

import java.io.Serializable;

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
public class MemberSignSuccessVO implements Serializable{
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
