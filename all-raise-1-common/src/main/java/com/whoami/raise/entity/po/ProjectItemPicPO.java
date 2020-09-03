package com.whoami.raise.entity.po;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 项目详情图片表
 * @author whoami
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectItemPicPO implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer id;

    /*
     * 项目id
     */
    private Integer projectid;

    /*
     * 图片地址
     */
    private String itemPicPath;

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

    public String getItemPicPath() {
        return itemPicPath;
    }

    public void setItemPicPath(String itemPicPath) {
        this.itemPicPath = itemPicPath == null ? null : itemPicPath.trim();
    }
}