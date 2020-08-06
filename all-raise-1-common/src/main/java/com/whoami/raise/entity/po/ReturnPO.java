package com.whoami.raise.entity.po;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * �ر���Ϣ��
 * @author whoami
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReturnPO {
    private Integer id;

    /*
     * ��Ŀid
     */
    private Integer projectid;

    /*
     * 0 - ʵ��ر��� 1 ������Ʒ�ر�
     */
    private Byte type;

    /*
     * ֧�ֽ��
     */
    private Integer supportmoney;

    /*
     * �ر�����
     */
    private String content;

    /*
     * �ر���Ʒ�޶��0��Ϊ���޻ر�����
     */
    private Integer count;

    /*
     * �Ƿ����õ����޹�
     */
    private Integer signalpurchase;

    /*
     * �����޹�����
     */
    private Integer purchase;

    /*
     * �˷ѣ� 0 Ϊ����
     */
    private Integer freight;

    /*
     * 0 - ������Ʊ�� 1 - ����Ʊ
     */
    private Byte invoice;

    /*
     * ��Ŀ�������������֧���߷��ͻر�
     */
    private Integer returndate;

    /*
     * ˵��ͼƬ·��
     */
    private String describPicPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getSupportmoney() {
        return supportmoney;
    }

    public void setSupportmoney(Integer supportmoney) {
        this.supportmoney = supportmoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSignalpurchase() {
        return signalpurchase;
    }

    public void setSignalpurchase(Integer signalpurchase) {
        this.signalpurchase = signalpurchase;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Byte getInvoice() {
        return invoice;
    }

    public void setInvoice(Byte invoice) {
        this.invoice = invoice;
    }

    public Integer getReturndate() {
        return returndate;
    }

    public void setReturndate(Integer returndate) {
        this.returndate = returndate;
    }

    public String getDescribPicPath() {
        return describPicPath;
    }

    public void setDescribPicPath(String describPicPath) {
        this.describPicPath = describPicPath == null ? null : describPicPath.trim();
    }
}