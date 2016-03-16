package com.dianxin.imessage.biz.login;

import com.dianxin.imessage.dao.dataobject.login.UserLoginInfo;

public interface IVerifyLoginRestrictions {
	
	UserLoginInfo getUserLoginInfo(String userName);

	Boolean isCanLogin(String userName);

	void setWrongPasswdCount(String userName);

}
