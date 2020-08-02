package com.whoami.raise.entity.po;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 项目表
 * @author whoami
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectPO {
    private Integer id;

    /*
     * 项目名称
     */
    private String projectName;

    /*
     * 项目描述
     */
    private String projectDescription;

    /*
     * 筹集金额
     */
    private Long money;

    /*
     * 筹集天数
     */
    private Integer day;

    /*
     * 0-即将开始，1-众筹中，2-众筹成功，3-众筹失败
     */
    private Byte status;

    /*
     * 项目发起时间
     */
    private String deploydate;

    /*
     * 已筹集到的金额
     */
    private Long supportmoney;

    /*
     * 支持人数
     */
    private Integer supporter;

    /*
     * 百分比完成度
     */
    private Integer completion;

    /*
     * 发起人会员id
     */
    private Integer memberid;

    /*
     * 项目创建时间
     */
    private String createdate;

    /*
     * 关注人数
     */
    private Integer follower;

    /*
     * 头图路径
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