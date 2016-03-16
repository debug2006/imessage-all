package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;
import java.util.List;

public class MyTags implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -193853827736141891L;
	private Integer uid;
	private List<TagInfo> tags;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public List<TagInfo> getTags() {
		return tags;
	}

	public void setTags(List<TagInfo> tags) {
		this.tags = tags;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}
}
