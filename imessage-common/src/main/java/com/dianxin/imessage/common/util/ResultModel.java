package com.dianxin.imessage.common.util;

import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.util.JSONUtil;

/**
 * 
 * @author b_fatty
 *
 * @param <T>
 */

public class ResultModel<T> {
	
	public static final boolean RESULT_SUCCESS_NUM = true;
	public static final boolean RESULT_FAIL_NUM = false;
	
	private boolean result = RESULT_SUCCESS_NUM;   //返回状态  成功或失败  默认成功
	
	private T data;      //返回的数据
	
	private String errCode = ErrCodeEnum.NORMAL.value; //异常状态码 默认无异常

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String toJSON(){
		return JSONUtil.toJSON(this);
	}
}
