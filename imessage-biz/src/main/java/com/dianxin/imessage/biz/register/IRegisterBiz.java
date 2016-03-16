package com.dianxin.imessage.biz.register;

import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.dataobject.RegistrationFormModel;

public interface IRegisterBiz extends IBaseBiz<DxUserModel> {

	/**
	 * 判断手机号码是否存在
	 * 
	 * @param phone
	 * @return 0：未注册 1： 已注册
	 */
	String existPhone(String phone);

	/**
	 * 注册用户信息
	 * 
	 * @param phone
	 * @return 注册成功，返回uid
	 */
	String register(RegistrationFormModel registrationFormModel);

}
