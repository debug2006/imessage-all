package com.dianxin.imessage.dao.dataobject;

import com.dianxin.imessage.dao.base.BaseModel;

/**
 * 
 * <br>
 * <b>功能：</b>映射类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Mon Jan 18 19:57:57 GMT+08:00 2016 <br>
 * 
 * @return id
 */
public class DxUserAbilityModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 超能力id
	 */
	private Integer abiliyId;

	/**
	 * 用户uid
	 */
	private Integer uid;

	/**
	 * get 超能力id
	 */
	public Integer getAbiliyId() {
		return abiliyId;
	}

	/**
	 * set 超能力id
	 */
	public void setAbiliyId(Integer abiliyId) {
		this.abiliyId = abiliyId;
	}

	/**
	 * get 用户uid
	 */
	public Integer getUid() {
		return uid;
	}

	/**
	 * set 用户uid
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}

}