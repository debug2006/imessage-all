package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;

public class FriendInfo implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 7068883105745614089L;

	/**
	 * 好友UID
	 */
	private String frienduid;

	/**
	 * 备注描述
	 */
	private String remarkDesc;

	public String getFrienduid() {
		return frienduid;
	}

	public void setFrienduid(String frienduid) {
		this.frienduid = frienduid;
	}

	public String getRemarkDesc() {
		return remarkDesc;
	}

	public void setRemarkDesc(String remarkDesc) {
		this.remarkDesc = remarkDesc;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
