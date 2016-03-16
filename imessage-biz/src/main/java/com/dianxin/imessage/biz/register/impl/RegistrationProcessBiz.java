package com.dianxin.imessage.biz.register.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.register.IRegistrationProcessBiz;
import com.dianxin.imessage.common.exception.userManager.RegisterException;
import com.dianxin.imessage.common.util.IdGenerator;
import com.dianxin.imessage.common.util.Identities;
import com.dianxin.imessage.common.util.ServicesEncodingUtils;
import com.dianxin.imessage.common.util.UserManageUtil;
import com.dianxin.imessage.common.util.ValidatorTools;
import com.dianxin.imessage.dao.dataobject.DxUserAbilityModel;
import com.dianxin.imessage.dao.dataobject.DxUserImageModel;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.dataobject.DxUserVersionModel;
import com.dianxin.imessage.dao.dataobject.RegistrationFormModel;
import com.dianxin.imessage.dao.mapper.UserMapper;

@Service
public class RegistrationProcessBiz implements IRegistrationProcessBiz {

	private Logger logger = LoggerFactory.getLogger(RegistrationProcessBiz.class);

	@Autowired
	private UserMapper<DxUserModel> mapper;

	private String userGenNum;

	private Integer uid;

	@Override
	public String getUserGenNum(RegistrationFormModel registrationFormModel) {

		String regMainVersion = UserManageUtil.getMainVersion(registrationFormModel.getRegVersion());

		String phoneAfterDigits = UserManageUtil
				.getPhoneAfterDigits(String.valueOf(registrationFormModel.getTelphone()), 4);

		if (null == regMainVersion || null == phoneAfterDigits) {
			logger.error("The RegVersion {},Telphone {} Invalid", registrationFormModel.getRegVersion(),
					registrationFormModel.getTelphone());
			return null;
		}

		// "注册渠道 1位" + "大版本号1位"+ "手机后4位" + Identities.random4num(4);
		StringBuffer userGenNums = new StringBuffer();
		userGenNums.append(registrationFormModel.getRegSource());
		userGenNums.append(regMainVersion);
		userGenNums.append(phoneAfterDigits);

		// TODO 该纸条号是否存在
		userGenNum = existUserGenNum(userGenNums, Identities.random4num(4));

		return userGenNum;
	}

	/**
	 * 获取系统生成的纸条号
	 * 
	 * @param userGenNumPrefix
	 * @param random
	 * @return
	 */
	private String existUserGenNum(StringBuffer userGenNumPrefix, String random) {
		logger.debug("getUid start");
		String userGenNumTemp = userGenNumPrefix.append(random).toString();
		Integer userGenNum = null;
		try {
			userGenNum = mapper.existUserGenNum(userGenNumTemp);

			if (null != userGenNum && userGenNum > 0) {
				existUserGenNum(userGenNumPrefix, Identities.random4num(4));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}

		return userGenNumTemp;
	}

	@Override
	public Integer getUid() {
		logger.debug("getUid start");
		// String userGenNum =
		if (null == userGenNum) {
			return null;
		}

		// String uid = ServicesEncodingUtils.encrypt(userGenNum, "Dx2016");
		uid = existUID(Integer.valueOf(IdGenerator.genUid()));
		return uid;
	}

	/**
	 * 获取系统生成的纸条号
	 * 
	 * @param userGenNumPrefix
	 * @param random
	 * @return
	 */
	private Integer existUID(Integer genUid) {

		Integer num = null;
		try {
			num = mapper.existUid(genUid);

			if (null != num && num > 0) {
				existUID(Integer.valueOf(IdGenerator.genUid()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
		return genUid;
	}

	@Override
	public DxUserModel getDxUserModel(RegistrationFormModel registrationFormModel) throws RegisterException {

		if (null == getUserGenNum(registrationFormModel)) {
			logger.debug("getUserGenNum is null");
			String errMgs = "getUserGenNum is null";
			logger.debug(errMgs);
			throw new RegisterException(errMgs);
		}

		if (null == getUid()) {
			logger.debug("getUid is null");

			String errMgs = "getUid is null";
			logger.debug(errMgs);
			throw new RegisterException(errMgs);
		}

		DxUserModel userModel = new DxUserModel();
		// TODO BeanUtils.copyProperties(userModel, registrationFormModel);

		// BeanUtils.copyProperties(userModel, registrationFormModel);

		userModel.setUid(uid);
		userModel.setUserGenNum(userGenNum);

		// 先简单实现，后优化
		userModel.setUserName(registrationFormModel.getUserName());
		userModel.setUserNum(userGenNum);
		userModel.setTelphone(registrationFormModel.getTelphone());

		// 密码进行MD5加密,加密密匙，从数据库配置表中获取
		// String dxpasswd =
		// ServicesEncodingUtils.encrypt(registrationFormModel.getPassword(),
		// "Dx2016");

		// 进行MD5加密
		String dxpasswd = ServicesEncodingUtils.encrypt(registrationFormModel.getPassword());

		userModel.setPassword(dxpasswd);
		userModel.setDistrictNumbe(registrationFormModel.getDistrictNumbe());
		userModel.setRegSource(registrationFormModel.getRegSource());
		userModel.setRegVersion(registrationFormModel.getRegVersion());
		userModel.setIsDel(1);
		userModel.setSearchTelFalg(1);
		userModel.setSearchNumFalg(1);
		userModel.setConstellationType("1");
		userModel.setSex(0);

		return userModel;
	}

	@Override
	public DxUserVersionModel getDxUserVersion(RegistrationFormModel registrationFormModel) {

		if (null == uid || null == userGenNum) {
			return null;
		}

		DxUserVersionModel userVersion = new DxUserVersionModel();
		userVersion.setVesionDesc(registrationFormModel.getVesionDesc());
		userVersion.setImei(registrationFormModel.getImei());
		userVersion.setModel(registrationFormModel.getModel());
		userVersion.setSysName(registrationFormModel.getSysName());
		userVersion.setSysVersion(registrationFormModel.getSysVersion());
		userVersion.setUid(uid);
		userVersion.setVersion(registrationFormModel.getRegVersion());

		// userVersion.set

		return userVersion;
	}

	@Override
	public DxUserAbilityModel getDxUserAbility(RegistrationFormModel registrationFormModel) {

		if (null == uid || null == userGenNum) {
			return null;
		}

		DxUserAbilityModel userAbilityModel = new DxUserAbilityModel();
		userAbilityModel.setUid(uid);
		userAbilityModel.setAbiliyId(Integer.valueOf(registrationFormModel.getAbilityId()));

		return userAbilityModel;
	}

	@Override
	public Boolean validateRegistrationForm(RegistrationFormModel registrationFormModel) {

		// TODO 校验注册信息（手机号，昵称，密码，超能力ID，。IMEI）

		if (!ValidatorTools.isMobile(registrationFormModel.getTelphone().toString())) {

		}
		registrationFormModel.getUserName();
		registrationFormModel.getPassword();
		registrationFormModel.getAbilityId();
		registrationFormModel.getImei();

		// TODO 后续统一校验
		return true;
	}

	@Override
	public DxUserImageModel getDxUserImageModel(RegistrationFormModel registrationFormModel) throws Exception {

		DxUserImageModel userImageModel = null;

		if (StringUtils.isNotEmpty(registrationFormModel.getPhotoPath())) {

			userImageModel = new DxUserImageModel();

			userImageModel.setFilePath(registrationFormModel.getPhotoPath());

			userImageModel.setUid(uid);

		}

		return userImageModel;
	}
}