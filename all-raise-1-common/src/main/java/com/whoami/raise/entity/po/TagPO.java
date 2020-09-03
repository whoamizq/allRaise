package com.whoami.raise.entity.po;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ��ǩ��
 * @author whoami
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TagPO implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    /*
     * ��ǩid
     */
    private Integer pid;

    /*
     * ��ǩ����
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}