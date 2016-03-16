package com.dianxin.imessage.biz.register;

import com.dianxin.imessage.dao.dataobject.DxUserAbilityModel;
import com.dianxin.imessage.dao.dataobject.DxUserImageModel;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.dataobject.DxUserVersionModel;
import com.dianxin.imessage.dao.dataobject.RegistrationFormModel;

/**
 * 
 * @author kai.fantasy
 *
 */
public interface IRegistrationProcessBiz {

	Boolean validateRegistrationForm(RegistrationFormModel registrationFormModel) throws Exception;

	String getUserGenNum(RegistrationFormModel registrationFormModel);

	Integer getUid();

	DxUserModel getDxUserModel(RegistrationFormModel registrationFormModel) throws Exception;

	DxUserVersionModel getDxUserVersion(RegistrationFormModel registrationFormModel) throws Exception;

	DxUserAbilityModel getDxUserAbility(RegistrationFormModel registrationFormModel) throws Exception;

	DxUserImageModel getDxUserImageModel(RegistrationFormModel registrationFormModel) throws Exception;

}
