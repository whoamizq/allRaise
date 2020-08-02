package com.whoami.raise.entity.po;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 标签表
 * @author whoami
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TagPO {
    private Integer id;

    /*
     * 标签id
     */
    private Integer pid;

    /*
     * 标签名称
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