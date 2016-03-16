package com.dianxin.imessage.common.util;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RSAKeyStoreTest {

	private RSAKeyStore store;
	
	@Before
	public void setUp(){
		store = new RSAKeyStore();
		try {
			store.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown(){
		store = null;
	}
	
	//@Test
	public void testEncrypt(){
		/*String str = store.encrypt("{\"str\":\"p1\",\"i\":1,\"p\":{\"str\":null,\"i\":0,\"p\":null,\"l\":null},\"l\":[]}");
		String str2 = store.encrypt("{\"p\":null,\"l\":[{\"str\":\"p1\",\"i\":1,\"p\":{\"str\":null,\"i\":0,\"p\":null,\"l\":null},\"l\":[]}]}");
		*/
		System.out.println(store.encrypt("123456"));
	}
	@Test
	public void testDecrypt(){
		String str =  "{\"p\":null,\"l\":[{\"str\":\"p1\",\"i\":1,\"p\":{\"str\":null,\"i\":0,\"p\":null,\"l\":null},\"l\":[]}]}";
		System.out.println(str.length());
		long before = System.currentTimeMillis();
		String testData = store.encrypt(str);
		long after = System.currentTimeMillis();
		System.out.println("加密耗时：" + (after - before) + "ms");
		before = System.currentTimeMillis();
		store.decrypt(testData);
		after = System.currentTimeMillis();
		System.out.println(store.decrypt(testData));
		System.out.println("解密共耗时：" + (after - before) + "ms");
	}
}
