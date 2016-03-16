package com.dianxin.imessage.common.exception.userManager;

import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.exception.BaseException;

public class RegisterException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegisterException(String message) {
		super(message);
		setErrCode(ErrCodeEnum.REGISTRATION_FAILED.value);
	}

}
