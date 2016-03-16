package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 好友在我的分组列表中
 * 
 * @author kai.fantasy
 */
public class TagsForFriend implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户UID
	 */
	private Integer uid;

	private FriendInfo friendInfo;

	/**
	 * 标签名称
	 */
	private List<String> tagNames;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

	public FriendInfo getFriendInfo() {
		return friendInfo;
	}

	public void setFriendInfo(FriendInfo friendInfo) {
		this.friendInfo = friendInfo;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
