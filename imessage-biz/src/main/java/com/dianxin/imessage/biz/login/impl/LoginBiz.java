package com.dianxin.imessage.biz.login.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.login.ILoginBiz;
import com.dianxin.imessage.biz.login.IVerifyLoginRestrictions;
import com.dianxin.imessage.common.cache.Memcached;
import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.util.ServicesEncodingUtils;
import com.dianxin.imessage.common.util.UserManageUtil;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.mapper.UserMapper;

/**
 * 登录服务
 * 
 * @author kai.fantasy
 *
 */
@Service
public class LoginBiz extends BaseBiz<DxUserModel>implements ILoginBiz {

	private Logger logger = LoggerFactory.getLogger(LoginBiz.class);

	@Autowired
	private UserMapper<DxUserModel> mapper;

	@Autowired
	private IUserBiz userBiz;

	@Autowired
	private MemcachedFactory memcacheFactory;

	@Autowired
	private IVerifyLoginRestrictions verifyLoginRestrictions;

	/**
	 * @return
	 */
	public BaseMapper<DxUserModel> getMapper() {
		return mapper;
	}

	/**
	 * 用户登录
	 * 
	 * @param userName
	 * @param password
	 * @return 登录成功返回uid，-1：用户不存在，-2：密码错误
	 */
	@Override
	public String login(String userName, String password) {

		// 登录次数检查
		if (!verifyLoginRestrictions.isCanLogin(userName)) {
			// 登录账号信息校验失败(非手机号，非纸条号)
			return ErrCodeEnum.HAS_LIMITED_LOGIN.value;
		}

		// 判断登录账号为 手机号/纸条号
		Map<String, Object> map = UserManageUtil.getLoginAccount(userName);

		if (null == map) {
			// 登录账号信息校验失败(非手机号，非纸条号)
			return ErrCodeEnum.LOGIN_ACCOUNT_INVALID.value;
		}

		Integer uid = 0;

		try {
			DxUserModel userModel = mapper.queryUserInfoByMap(map);

			if (null == userModel) {
				// userModel 为 null 1005:用户不存在
				return ErrCodeEnum.USER_NOT_EXIST.value;
			}

			// 密码进行MD5加密,加密密匙，从数据库配置表中获取
			// String dxpasswd = ServicesEncodingUtils.encrypt(password,
			// "Dx2016");

			// 密码进行MD5加密，与数据库进比对
			String dxpasswd = ServicesEncodingUtils.encrypt(password);

			if (!userModel.getPassword().equals(dxpasswd)) {

				// 设置登录密码错误次数记录
				verifyLoginRestrictions.setWrongPasswdCount(userName);

				return ErrCodeEnum.PASSWORD_ERROR.value;
			}

			uid = userModel.getUid();

			// 登录成功将密码明文保存到memcache中
			Memcached memcache = memcacheFactory.createMemcached();
			memcache.set(uid + "_pwd", password);

			userBiz.getXmppManagerForPool(uid);
			// userBiz.getXmppManagerForPool(uid, password);

		} catch (Exception e) {
			logger.error("{} login is error ", userName, e);
			return ErrCodeEnum.SERVICE_ERROR.value;
		}
		return uid.toString();
	}

}
