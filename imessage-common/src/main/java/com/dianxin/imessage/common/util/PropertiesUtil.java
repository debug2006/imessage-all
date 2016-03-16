package com.dianxin.imessage.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * =============================================================================
 * = Copyright (c) 2015 by www.dianxin.com, All rights reserved.
 * =============================================================================
 * = This software is the confidential and proprietary information of
 * tencent.com, Inc. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with dianxin.com, Inc.
 * -----------------------------------------------------------------------------
 * -
 * <p/>
 * Author: b_fatty Date: 2015/12/18 Description: Properties配置文件工具类 Nothing.
 * Function List: 1. Nothing. History: 1. Nothing.
 * =============================================================================
 * =
 */
public class PropertiesUtil {

	public final static String DX_APP_PRTPERTIES_PATH = getWEBINFPath() + "app.properties";

	public final static String DISPLAYHTML_CONF_PATH = getWEBINFPath() + "displayHtml.properties";

	/**
	 * 获取配置文件键值对
	 * 
	 * @return
	 */
	public static Map<String, String> getPropertiesValue(String filePath, String[] propertyNames) {

		Map<String, String> result = new HashMap<String, String>();

		Properties prop = new Properties();
		InputStream in = null;

		try {
			in = new FileInputStream(filePath);
			prop.load(in);
			for (String key : propertyNames) {
				if (null != prop.getProperty(key)) {
					result.put(key, prop.getProperty(key).trim());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	/**
	 * 获取配置文件值
	 * 
	 * @return
	 */
	public static String getPropertiesValue(String filePath, String key) {
		String value = null;

		Properties prop = new Properties();
		InputStream in = null;

		try {
			in = new FileInputStream(filePath);
			prop.load(in);
			if (null != prop.getProperty(key))
				value = prop.getProperty(key).trim();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return value;
	}

	/**
	 * 获取WEB-INF/conf目录路径
	 * 
	 * @return
	 */
	public static String getWEBINFPath() {
		String path = null;
		String url = PropertiesUtil.class.getResource("/").getPath().replaceAll("%20", " ");
		if (url.indexOf("WEB-INF") >= 0) {
			path = url.substring(0, url.indexOf("WEB-INF")) + "WEB-INF/conf/";
		}
		return path;
	}

}
