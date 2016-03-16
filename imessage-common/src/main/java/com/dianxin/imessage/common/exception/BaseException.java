package com.dianxin.imessage.common.exception;
/**
 * 
 * @author b_fatty
 * 
 * 自定义异常基类
 * 
 * Date : 2016/1/20
 *
 */
public class BaseException extends Exception{

	private static final long serialVersionUID = 1L;

	private String errCode;
	
	private String message;
	
	private Exception errCause;
	
	public BaseException(String message){
		super(message);
		this.message = message;
	}
	
	public BaseException(Exception e){
		super(e);
		this.errCause = e;
	}
	
	public BaseException(String message,Exception e){
		super(message,e);
		this.message = message;
		this.errCause = e;
	}

	public BaseException(String message,String errCode,Exception e){
		super(message,e);
		this.message = message;
		this.errCode = errCode;
		this.errCause = e;
	}
	
	public BaseException(String message,String errCode){
		super(message);
		this.message = message;
		this.errCode = errCode;
	}
	
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public Exception getErrCause() {
		return errCause;
	}

	public void setErrCause(Exception errCause) {
		this.errCause = errCause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
