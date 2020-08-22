package com.whoami.raise.entity.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO implements Serializable {
	private String loginacct;
	
	private String userpswd;
	
	private String phoneNum;
	
	private String randomCode;
}
