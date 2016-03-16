package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;

public class UserInterest implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1016153522977348975L;

	/**
	 * 用户Uid
	 */
	private Integer uid;

	/**
	 * 兴趣描述
	 */
	private String interest;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
