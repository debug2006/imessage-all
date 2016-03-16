package com.dianxin.imessage.dao.dataobject.config;

import java.io.Serializable;

public class UserAbility implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 7080306742517394096L;

	/**
	 * 用户Uid
	 */
	private int abilityId;

	/**
	 * 超能力名称
	 */
	private String abilityName;

	/**
	 * 超能力说明
	 */
	private String abilityDesc;

	public int getAbilityId() {
		return abilityId;
	}

	public void setAbilityId(int abilityId) {
		this.abilityId = abilityId;
	}

	public String getAbilityName() {
		return abilityName;
	}

	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}

	public String getAbilityDesc() {
		return abilityDesc;
	}

	public void setAbilityDesc(String abilityDesc) {
		this.abilityDesc = abilityDesc;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}
