package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;
import java.util.List;

public class MyTagList implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -186325378291230977L;

	/**
	 * uid
	 */
	private String uid;

	/**
	 * 标签数量
	 */
	private int tagNum;

	/**
	 * 标签信息
	 */
	private List<Tag> tags;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getTagNum() {
		return tagNum;
	}

	public void setTagNum(int tagNum) {
		this.tagNum = tagNum;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
