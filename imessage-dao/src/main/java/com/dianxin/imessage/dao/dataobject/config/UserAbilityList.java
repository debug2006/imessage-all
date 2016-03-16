package com.dianxin.imessage.dao.dataobject.config;

import java.io.Serializable;
import java.util.List;

public class UserAbilityList implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -3668395479779764840L;

	/**
	 * 总数
	 */
	private Integer total;

	/**
	 * 超能力列表
	 */
	private List<UserAbility> userAbilityList;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<UserAbility> getUserAbilityList() {
		return userAbilityList;
	}

	public void setUserAbilityList(List<UserAbility> userAbilityList) {
		this.userAbilityList = userAbilityList;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
