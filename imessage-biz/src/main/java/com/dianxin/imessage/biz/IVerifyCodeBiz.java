package com.dianxin.imessage.biz;

public interface IVerifyCodeBiz {

	boolean getModPWDVerifyCode(String phone) throws Exception;
	
	boolean getRegisterVerifyCode(String phone) throws Exception;
	
	boolean checkVerifyCode(String phone,String verifyCode) throws Exception;
}
