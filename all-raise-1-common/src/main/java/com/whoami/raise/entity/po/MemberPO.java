package com.whoami.raise.entity.po;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberPO {
	/**
	 * 主键id
	 */
    private Integer id;

    /**
     * 登录账户
     */
    private String loginacct;

    /*
     * 用户密码
     */
    private String userpswd;

    /*
     * 用户名称
     */
    private String username;

    /*
     * 邮箱
     */
    private String email;

    /*
     * 授权状态
     */
    private Byte authstatus;

    /*
     * 用户状态
     */
    private Byte usertype;

    private String realname;

    private String cardnum;

    private Byte accttype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct == null ? null : loginacct.trim();
    }

    public String getUserpswd() {
        return userpswd;
    }

    public void setUserpswd(String userpswd) {
        this.userpswd = userpswd == null ? null : userpswd.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Byte getAuthstatus() {
        return authstatus;
    }

    public void setAuthstatus(Byte authstatus) {
        this.authstatus = authstatus;
    }

    public Byte getUsertype() {
        return usertype;
    }

    public void setUsertype(Byte usertype) {
        this.usertype = usertype;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum == null ? null : cardnum.trim();
    }

    public Byte getAccttype() {
        return accttype;
    }

    public void setAccttype(Byte accttype) {
        this.accttype = accttype;
    }
}