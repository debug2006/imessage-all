package com.dianxin.imessage.dao.dataobject.book;

import java.io.Serializable;
import java.util.List;

public class UserInterestList implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -6500652980297401373L;

	/**
	 * 总数
	 */
	private Integer total;

	private List<UserInterest> userInterestList;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<UserInterest> getUserInterestList() {
		return userInterestList;
	}

	public void setUserInterestList(List<UserInterest> userInterestList) {
		this.userInterestList = userInterestList;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
