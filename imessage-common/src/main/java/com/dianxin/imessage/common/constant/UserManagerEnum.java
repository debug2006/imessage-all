package com.dianxin.imessage.common.constant;

public enum UserManagerEnum {

	/** 手机已注册 */
	PHONE_REGISTERED("1"), 
	/** 手机未注册 */
	PHONE_NOT_REGISTERED("0")

	;

	public final String value;

	private UserManagerEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
