package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;

public class Tag implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 2108304620220220855L;

	/**
	 * 标签名称
	 */
	private String tagName;

	/**
	 * 标签好友的数量
	 */
	private int friendNum;

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getFriendNum() {
		return friendNum;
	}

	public void setFriendNum(int friendNum) {
		this.friendNum = friendNum;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
