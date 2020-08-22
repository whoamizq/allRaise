package com.whoami.raise.entity.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ��װ ����vo����
 * @author whoami
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLauchInfoVO  implements Serializable{
	
	// �û���¼ϵͳ��ϵͳ�����tokenֵ������ʶ���û���ݡ�
	// �û���id���Ը���tokenֵ��ѯRedis�õ�
	private String memberSignToken;

	// ��Redis����ʱ�洢��Ŀ���ݵ�tokenֵ
	private String projectTempToken;
		
	// �򵥽���
	private String descriptionSimple;
		
	// ��ϸ����
	private String descriptionDetail;
		
	// ��ϵ�绰
	private String phoneNum;
		
	// �ͷ��绰
	private String serviceNum;

}
