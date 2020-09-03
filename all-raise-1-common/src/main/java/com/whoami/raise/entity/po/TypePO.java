package com.whoami.raise.entity.po;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * �����
 * @author whoami
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TypePO implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    /*
     * ��������
     */
    private String name;

    /*
     * �������
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