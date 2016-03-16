package com.dianxin.imessage.common.util;

import com.dianxin.imessage.common.exception.userManager.UidFormatException;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>用户相关工具方法<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b>2016/1/14<br>
 * <b>版权所有：<b>dianxin.com<br>
 */
public class UserManageUtil {

	/**
	 * 判断用户登录方式
	 * 
	 * @param userName
	 * @return
	 */
	public static Map<String, Object> getLoginAccount(String userName) {

		Map<String, Object> map = null;

		// 符合手机号判断
		if (ValidatorTools.isMobile(userName)) {
			map = new HashMap<String, Object>();
			map.put("telphone", userName);
		}
		// 符合纸条号
		else if (ValidatorTools.isUsername(userName)) {
			map = new HashMap<String, Object>();
			map.put("user_num", userName);
		}

		return map;
	}

	/**
	 * 获取主版本值
	 * 
	 * @param regVersion
	 * @return
	 */
	public static String getMainVersion(String regVersion) {

		if ("VBeta".equals(regVersion)) {
			return "0";
		}

		if (!ValidatorTools.isDxSoftVersion(regVersion)) {
			return null;
		}

		String versionPix[] = regVersion.split("\\.");

		String mainVersion = versionPix[0].substring(1);

		return mainVersion;
	}

	/**
	 * 获取号码后面几位
	 * 
	 * @param phone
	 * @param i
	 * @return
	 */
	public static String getPhoneAfterDigits(String phone, int i) {

		if (!ValidatorTools.isMobile(phone) || phone.length() < i) {
			return null;
		}

		String phoneAfterDigits = phone.substring(phone.length() - i, phone.length());

		return phoneAfterDigits;
	}
	
	/**
	 * 判断前台传的uid(格式校验	登录现不需校验)
	 * 
	 * @author b_fatty
	 * 
	 * @exception UidFormatException
	 */
	
	public static void checkUid(Integer uid) throws Exception{
		System.out.print("uid========="+uid);
		
		boolean isFormat = ValidatorTools.isUserUid(uid.toString());
		
		if(!isFormat){
			throw new UidFormatException();
		}
	}
}
