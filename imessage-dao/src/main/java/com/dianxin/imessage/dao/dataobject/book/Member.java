package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;

public class Member implements Serializable {

	private static final long serialVersionUID = 8083525747226247800L;

	private Integer uid;

	private String nickName;
	private String headUrl;
	private String role;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
}
