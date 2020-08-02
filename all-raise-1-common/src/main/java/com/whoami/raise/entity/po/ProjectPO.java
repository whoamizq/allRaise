package com.whoami.raise.entity.po;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ��Ŀ��
 * @author whoami
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectPO {
    private Integer id;

    /*
     * ��Ŀ����
     */
    private String projectName;

    /*
     * ��Ŀ����
     */
    private String projectDescription;

    /*
     * �Ｏ���
     */
    private Long money;

    /*
     * �Ｏ����
     */
    private Integer day;

    /*
     * 0-������ʼ��1-�ڳ��У�2-�ڳ�ɹ���3-�ڳ�ʧ��
     */
    private Byte status;

    /*
     * ��Ŀ����ʱ��
     */
    private String deploydate;

    /*
     * �ѳＯ���Ľ��
     */
    private Long supportmoney;

    /*
     * ֧������
     */
    private Integer supporter;

    /*
     * �ٷֱ���ɶ�
     */
    private Integer completion;

    /*
     * �����˻�Աid
     */
    private Integer memberid;

    /*
     * ��Ŀ����ʱ��
     */
    private String createdate;

    /*
     * ��ע����
     */
    private Integer follower;

    /*
     * ͷͼ·��
     */
    private String headerPicturePath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription == null ? null : projectDescription.trim();
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getDeploydate() {
        return deploydate;
    }

    public void setDeploydate(String deploydate) {
        this.deploydate = deploydate == null ? null : deploydate.trim();
    }

    public Long getSupportmoney() {
        return supportmoney;
    }

    public void setSupportmoney(Long supportmoney) {
        this.supportmoney = supportmoney;
    }

    public Integer getSupporter() {
        return supporter;
    }

    public void setSupporter(Integer supporter) {
        this.supporter = supporter;
    }

    public Integer getCompletion() {
        return completion;
    }

    public void setCompletion(Integer completion) {
        this.completion = completion;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public String getHeaderPicturePath() {
        return headerPicturePath;
    }

    public void setHeaderPicturePath(String headerPicturePath) {
        this.headerPicturePath = headerPicturePath == null ? null : headerPicturePath.trim();
    }
}