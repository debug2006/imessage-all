package com.dianxin.imessage.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.IVerifyCodeBiz;
import com.dianxin.imessage.common.cache.Memcached;
import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.exception.MemcachedException;
import com.dianxin.imessage.common.util.RandomNumUtil;
import com.dianxin.imessage.common.util.SmsUtil;
/**
 * 短信验证码服务
 * 
 * @author b_fatty
 *
 */
@Service
public class VerifyCodeBiz implements IVerifyCodeBiz{

	private static final String PREFIX = "VC_";
	private static final int EXP = 120;  //验证码过期时间为120s
	
	private static final int SEND_TYPE_MODPWD = 1;
	private static final int SEND_TYPE_REGISTER = 2;
	
	@Autowired
	private MemcachedFactory cacheFactory;
	
	@Override
	public boolean getModPWDVerifyCode(String phone) throws Exception{
		
		return getVerifyCode(phone,SEND_TYPE_MODPWD);
	}
	
	@Override
	public boolean getRegisterVerifyCode(String phone) throws Exception{
		return getVerifyCode(phone,SEND_TYPE_REGISTER);
	}
	
	@Override
	public boolean checkVerifyCode(String phone, String verifyCode) throws Exception{
		
		Memcached cache = cacheFactory.createMemcached();
		String code = (String)cache.get(PREFIX + phone);
		return null!=code&&code.equals(verifyCode);
	}
	
	public boolean getVerifyCode(String phone,int type) throws Exception{
		
		//TODO 判断手机号码是否符合要求
		
		String code = RandomNumUtil.createRandonNum(5);
		boolean bool = false;
		switch (type) {
		case SEND_TYPE_MODPWD:
			bool = SmsUtil.sendModPwd(phone, code);
			break;
		case SEND_TYPE_REGISTER:
			bool = SmsUtil.sendRegister(phone, code);
			break;
		}
		 
		if(bool){
			Memcached cache = cacheFactory.createMemcached();
			cache.set(PREFIX + phone, code, EXP);
		}
		return bool;
	}
}
