package com.dianxin.imessage.common.util;

import java.util.regex.Pattern;

/**
 * 
 * <br>
 * <b>功能：</b>正则校验工具类<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b>2016/1/14<br>
 * <b>版权所有：<b>dianxin.com<br>
 */
public class ValidatorTools {

	/**
	 * 正则表达式：验证用户名
	 */
	public static final String REGEX_USERNAME = "待确认";

	/**
	 * 正则表达式：验证密码
	 */
	public static final String REGEX_PASSWORD = "^[\\w!@#$%^&*()_+~ ]{6,20}$";

	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE = "^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$";

	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 正则表达式：验证汉字
	 */
	public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

	/**
	 * 正则表达式：验证URL
	 */
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

	/**
	 * 正则表达式：验证IP地址
	 */
	public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

	/**
	 * 正则表达式：验证纸条君版本号格式
	 */
	public static final String DX_SOFT_VERSION = "v\\d+(\\.\\d+){0,3}";

	/**
	 * 正则表达式：验用户表UID的格式 ^InstanceId\d{8}$
	 */
	public static final String DX_USER_UID = "^" + IdGenerator.getInstanceId() + "\\d{8}$";

	/**
	 * 字母/数字
	 */
	public static final String alphanumeric = "^[A-Za-z0-9]+$";

	/**
	 * 数字
	 */
	public static final String number = "^[0-9]+$";

	/**
	 * 字母
	 */
	public static final String alphabet = "^[A-Za-z]+$";

	/**
	 * 经纬度
	 */
	public static final String JINGWEIDU = "(-|\\+)?(180.000000|(\\d{1,2}|1([0-7]\\d))\\.\\d{6})";

	/**
	 * 校验用户名
	 * 
	 * @param username
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUsername(String username) {
		return Pattern.matches(REGEX_USERNAME, username);
	}

	/**
	 * 校验密码
	 * 
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword(String password) {
		return Pattern.matches(REGEX_PASSWORD, password);
	}

	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		return Pattern.matches(REGEX_MOBILE, mobile);
	}

	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 校验汉字
	 * 
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isChinese(String chinese) {
		return Pattern.matches(REGEX_CHINESE, chinese);
	}

	/**
	 * 校验身份证
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard) {
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}

	/**
	 * 校验URL
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl(String url) {
		return Pattern.matches(REGEX_URL, url);
	}

	/**
	 * 校验IP地址
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean isIPAddr(String ipAddr) {
		return Pattern.matches(REGEX_IP_ADDR, ipAddr);
	}

	/**
	 * 校验纸条号软件版本格式
	 * 
	 * @param version
	 * @return
	 */
	public static boolean isDxSoftVersion(String version) {
		return Pattern.matches(DX_SOFT_VERSION, version);
	}

	/**
	 * 校验Uid格式
	 * 
	 * @param version
	 * @return
	 */
	public static boolean isUserUid(String uid) {
		return Pattern.matches(DX_USER_UID, uid);
	}

	/**
	 * 校验经纬度
	 * @param lon
	 * @param lat
	 * @return
	 */
	public static boolean isJingweidu(String lon,String lat)
	{
		return Pattern.matches(JINGWEIDU,lat)&&Pattern.matches(JINGWEIDU,lon);

	}
}

