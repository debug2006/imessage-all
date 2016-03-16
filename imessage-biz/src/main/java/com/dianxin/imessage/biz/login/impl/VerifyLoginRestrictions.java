package com.dianxin.imessage.biz.login.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.login.IVerifyLoginRestrictions;
import com.dianxin.imessage.common.cache.Memcached;
import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.exception.MemcachedException;
import com.dianxin.imessage.common.util.DateUtil;
import com.dianxin.imessage.dao.dataobject.login.UserLoginInfo;

@Service
public class VerifyLoginRestrictions implements IVerifyLoginRestrictions {

	@Autowired
	private MemcachedFactory memcachedFactory;

	private final static String CACHETAG = "_loginInfo";

	/**
	 * 判断用户登录是否已经锁定
	 */
	@Override
	public Boolean isCanLogin(String userName) {

		// 获取登录验证信息
		UserLoginInfo userLoginInfo = getUserLoginInfo(userName);

		Boolean isCanLogin = true;
		try {

			if (null != userLoginInfo) {
				boolean isLock = userLoginInfo.getIsLock();

				boolean isExpired = checkExpiration(userLoginInfo);

				// 锁定，且未过锁定有效期
				isCanLogin = isLock && !isExpired ? false : true;

			}
		} catch (Exception e) {
			// TODO 记录日志
			e.printStackTrace();
		}

		return isCanLogin;
	}

	/**
	 * 
	 * 检查锁定的时间是否已经超期
	 * 
	 * @param lockTime
	 * @param userName
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	private boolean checkExpiration(UserLoginInfo userLoginInfo) {

		// 是否过锁定有效期期
		boolean isExpired = false;
		String nowTime = DateUtil.getLocalTimeFromUTC(new Date());
		int min = 0;
		// 获取获取当前时间与 锁定时间的分钟数
		try {
			min = DateUtil.getMinuteDiff(userLoginInfo.getLockTime(), nowTime);

			// 低于30分钟，则未过期（后续可配置）
			isExpired = min > 30 ? true : false;

			// 超期后，清空登录验证信息;
			removeUserLoginWrongCache(isExpired, userLoginInfo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isExpired;
	}

	/**
	 * 
	 * 删除登录错误的缓存信息
	 * 
	 * @param isExpired
	 * @param userLoginInfo
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	private void removeUserLoginWrongCache(boolean isExpired, UserLoginInfo userLoginInfo) {

		// 清楚记录的登录错误次数信息
		if (isExpired) {

			try {

				userLoginInfo.setFailCount(0);
				userLoginInfo.setIsLock(false);

				Memcached memcache = memcachedFactory.createMemcached();

				memcache.delete(userLoginInfo.getUserName() + CACHETAG);

			} catch (MemcachedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public UserLoginInfo getUserLoginInfo(String userName) {

		UserLoginInfo userLoginInfo = null;
		Memcached memcache;
		try {
			memcache = memcachedFactory.createMemcached();

			userLoginInfo = (UserLoginInfo) memcache.get(userName + CACHETAG);

		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userLoginInfo;
	}

	@Override
	public void setWrongPasswdCount(String userName) {

		// 获取登录验证信息
		UserLoginInfo userLoginInfo = getUserLoginInfo(userName);

		if (null != userLoginInfo) {

			andFailCount(userLoginInfo);

		} else {

			// 第一次的密码输错记录
			userLoginInfo = new UserLoginInfo();
			userLoginInfo.setFailCount(1);
			userLoginInfo.setIsLock(false);
			String firstWrongTime = DateUtil.getLocalTimeFromUTC(new Date());
			userLoginInfo.setLockTime(firstWrongTime);
			userLoginInfo.setUserName(userName);
		}

		setUserLoginWrongCache(userName, userLoginInfo);

	}

	/**
	 * 
	 * 设置的登录错误信息到缓存中
	 * 
	 * @param userName
	 * @param userLoginInfo
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	private void setUserLoginWrongCache(String userName, UserLoginInfo userLoginInfo) {

		// 登录的错误信息设置到memcache中
		try {
			Memcached memcache = memcachedFactory.createMemcached();

			memcache.set(userName + CACHETAG, userLoginInfo);

		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 添加登录密码错误次数记录 ,如果超过5次，则锁定登录
	 * 
	 * @param userLoginInfo
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	private void andFailCount(UserLoginInfo userLoginInfo) {
		Integer failCount = userLoginInfo.getFailCount();
		userLoginInfo.setFailCount(failCount + 1);

		// 输错密码超过5次，则锁定登录，并记录当前时间为锁定开始时间
		if (failCount + 1 > 4) {
			userLoginInfo.setIsLock(true);
			String lockTime = DateUtil.getLocalTimeFromUTC(new Date());
			userLoginInfo.setLockTime(lockTime);
		}

	}

}
