package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;
import java.util.List;

public class FriendsBirthdayList implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -8799251238133196494L;

	/**
	 * 用户UID
	 */
	private Integer uid;

	/**
	 * 标签好友的数量
	 */
	private int fnum;

	/*
	 * 好友信息
	 */
	private List<FriendInfo> friends;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public int getFnum() {
		return fnum;
	}

	public void setFnum(int fnum) {
		this.fnum = fnum;
	}

	public List<FriendInfo> getFriends() {
		return friends;
	}

	public void setFriends(List<FriendInfo> friends) {
		this.friends = friends;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
