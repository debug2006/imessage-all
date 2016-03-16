package com.dianxin.imessage.biz.login;

import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxUserModel;

public interface ILoginBiz extends IBaseBiz<DxUserModel> {

	/**
	 * 用户登录
	 * 
	 * @param phone
	 * @return 登录成功，返回uid，用户不存在返回 -1，密码验证错误，返回-2
	 */
	String login(String username,String password);

}
