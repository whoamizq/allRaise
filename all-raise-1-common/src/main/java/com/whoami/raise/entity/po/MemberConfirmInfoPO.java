package com.whoami.raise.entity.po;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ������ȷ����Ϣ��
 * @author whoami
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberConfirmInfoPO implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    /*
     * ��Աid
     */
    private Integer memberid;

    /*
     * �׸�����ҵ�˺�
     */
    private String paynum;

    /*
     * �������֤��
     */
    private String cardnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getPaynum() {
        return paynum;
    }

    public void setPaynum(String paynum) {
        this.paynum = paynum == null ? null : paynum.trim();
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum == null ? null : cardnum.trim();
    }
}