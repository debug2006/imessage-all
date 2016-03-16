package com.dianxin.imessage.common.exception.userManager;

import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.exception.BaseException;

public class UidFormatException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UidFormatException(){
		super("uid format is wrong");
		setErrCode(ErrCodeEnum.UID_FORMAT_ERR.value);
	}
}
