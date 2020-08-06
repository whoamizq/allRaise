package com.whoami.raise.entity.po;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 项目发起人信息表
 * @author whoami
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberLaunchInfoPO {
    private Integer id;

    /*
     * 会员id
     */
    private Integer memberid;

    /*
     * 简单介绍
     */
    private String descriptionSimple;

    /*
     * 详情介绍
     */
    private String descriptionDetail;

    /*
     * 联系电话
     */
    private String phoneNum;

    /*
     * 客服电话
     */
    private String serviceNum;

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

    public String getDescriptionSimple() {
        return descriptionSimple;
    }

    public void setDescriptionSimple(String descriptionSimple) {
        this.descriptionSimple = descriptionSimple == null ? null : descriptionSimple.trim();
    }

    public String getDescriptionDetail() {
        return descriptionDetail;
    }

    public void setDescriptionDetail(String descriptionDetail) {
        this.descriptionDetail = descriptionDetail == null ? null : descriptionDetail.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(String serviceNum) {
        this.serviceNum = serviceNum == null ? null : serviceNum.trim();
    }
}