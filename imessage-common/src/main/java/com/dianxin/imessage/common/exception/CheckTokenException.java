package com.dianxin.imessage.common.exception;

import com.dianxin.imessage.common.constant.ErrCodeEnum;

public class CheckTokenException extends BaseException{

	private static final long serialVersionUID = 1L;

	public CheckTokenException(String message) {
		super(message);
		setErrCode(ErrCodeEnum.TOKEN_CHECK_FAIL.value);
	}

}
