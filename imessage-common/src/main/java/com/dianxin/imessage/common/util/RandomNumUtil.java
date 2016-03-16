package com.dianxin.imessage.common.util;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 验证码、各种编号生成工具
 * 
 * @author b_fatty
 * Date : 2016/1/14
 *
 */
public class RandomNumUtil {

	/**
	 * 创建指定位数的随机数
	 */
	public static String createRandonNum(int length){
		return RandomStringUtils.randomNumeric(length);
	}
	
	/**
	 * 创建UUID
	 */
	public static String createUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
