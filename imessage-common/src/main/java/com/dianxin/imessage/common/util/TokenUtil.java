package com.dianxin.imessage.common.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.dianxin.imessage.common.cache.Memcached;


/**
 * token工具类
 * @author chong.qin
 * */
public class TokenUtil {

	public static final String TOKEN_STRING_NAME = "token";
	private static final String FINANCE_TOKEN_KEY = "finance_token_";

	/**
	 * 保存token到memercache
	 * 
	 * @param token串
	 * */
	private static void saveTokenString(String tokenStr,Memcached cache) {
		cache.add(FINANCE_TOKEN_KEY + tokenStr, "true", 60*10);
	}

	/**
	 * 生成唯一token串
	 * */
	private static String generateTokenString() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 生成一个token保存到memercache,并返回该token
	 * 
	 * @return token
	 */
	public static String getTokenString(Memcached cache) {
		String tokenStr = generateTokenString();
		saveTokenString(tokenStr,cache);
		return tokenStr;
	}

	/**
	 * 检查是否标记字符串是有效的,如果会话令牌包含字符串,返回true。 否则, return false.
	 * 
	 * @param String
	 *            tokenStr
	 * @return true: 会话包含tokenStr; false: 会话是null或tokenStr id不在会话期间
	 */
	public static boolean isTokenStringValid(String tokenStr,Memcached cache) {
		if(StringUtils.isBlank(tokenStr)){
			return false;
		}
		boolean valid = false;
		Object obj = cache.get(FINANCE_TOKEN_KEY + tokenStr);
		if (null != obj) {
			valid = true;
			cache.delete(FINANCE_TOKEN_KEY + tokenStr);
		}
		return valid;
	}
}
