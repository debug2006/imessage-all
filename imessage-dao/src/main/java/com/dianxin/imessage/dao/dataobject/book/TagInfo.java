package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;
import java.util.List;

public class TagInfo implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -8799251238133196494L;

	/**
	 * 用户UID
	 */
	private Integer uid;

	/**
	 * 标签名称
	 */
	private String tagName;

	/**
	 * 原标签名（更改时）
	 */
	private String oldTagName;

	/*
	 * 标签组内好友信息
	 */
	private List<FriendInfo> friendsInfo;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<FriendInfo> getFriendsInfo() {
		return friendsInfo;
	}

	public void setFriendsInfo(List<FriendInfo> friendsInfo) {
		this.friendsInfo = friendsInfo;
	}

	public String getOldTagName() {
		return oldTagName;
	}

	public void setOldTagName(String oldTagName) {
		this.oldTagName = oldTagName;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
