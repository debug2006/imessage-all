package com.dianxin.imessage.common.util;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试类
 * 
 * @author b_fatty
 *
 */
public class PropertiesUtilTest {

	private String filePath;
	private String[] propertyNames;
	private String key;
	
	@Before
	public void setUp(){
		filePath = "F:/document/workspace/imessage-web/WebRoot/WEB-INF/conf/app.properties";
		propertyNames = new String[]{"app.version","app.downloadPath"};
		key = "app.version" ;
	}
	
	@After
	public void tearDown(){
		filePath = null;
		propertyNames = null;
		key = null;
	}
	
	@Test
	public void testGetPropertiesValue_1(){
		Map<String,String> result = PropertiesUtil.getPropertiesValue(filePath, propertyNames);
		System.out.println(result.get(propertyNames[0]));
		System.out.println(result.get(propertyNames[1]));
		assertEquals(result.get(propertyNames[0]),"V1.0.0");
		assertEquals(result.get(propertyNames[1]),"/data/app/V1.0.0");
	}
	
	@Test
	public void testGetPropertiesValue_2(){
		String value = PropertiesUtil.getPropertiesValue(filePath, key);
		System.out.println(value);
		assertEquals(value,"V1.0.0");
	}
}
