package com.whoami.raise.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * ��װ�ύvo����
 * @author whoami
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberConfirmInfoVO {
	// �û���¼ϵͳ��ϵͳ�����tokenֵ������ʶ���û����
	// �û���id���Ը���tokenֵ��ѯRedis�õ�
	private String memberSignToken;
	
	// ��Redis����ʱ�洢��Ŀ���ݵ�tokenֵ
	private String projectTempToken;
	
	// �׸����˺�
	private String paynum;
	
	// �������֤��
	private String cardnum;
}
