package com.whoami.raise.entity.po;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 分类表
 * @author whoami
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TypePO {
    private Integer id;

    /*
     * 分类名称
     */
    private String name;

    /*
     * 分类介绍
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}