package com.dianxin.imessage.common.exception;

import com.dianxin.imessage.common.constant.ErrCodeEnum;

public class MemcachedException extends BaseException{

	private static final long serialVersionUID = 1L;

	public MemcachedException(String message,Exception e) {
		super(message,e);
		setErrCode(ErrCodeEnum.MEMCACHED_ERR.value);
	}

}
