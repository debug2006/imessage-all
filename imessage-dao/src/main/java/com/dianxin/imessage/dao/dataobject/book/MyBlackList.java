package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;
import java.util.List;

public class MyBlackList implements Serializable {

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
	private int bnum;

	/*
	 * 黑名单信息
	 */
	private List<FriendInfo> blacklist;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public int getBnum() {
		return bnum;
	}

	public void setBnum(int bnum) {
		this.bnum = bnum;
	}

	public List<FriendInfo> getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(List<FriendInfo> blacklist) {
		this.blacklist = blacklist;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
