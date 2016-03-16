package com.dianxin.imessage.common.util;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class DESUtilTest {

	@Test
	public void test() throws UnsupportedEncodingException{
		//String str =  "{\"p\":null,\"l\":[{\"str\":\"p1\",\"i\":1,\"p\":{\"str\":null,\"i\":0,\"p\":null,\"l\":null},\"l\":[]}],\"str\":\"0\"}";
		String str =  "1069195600";
		long before = System.currentTimeMillis();
		String result = DESUtil.encrypt(str);
		String phone = Base64Utils.encode(result.getBytes());
		long after = System.currentTimeMillis();
		System.out.println("加密后的数据：" );
		System.out.println(phone);
		System.out.println("加密耗时：" + (after-before) + "ms");
		
		byte[] bytes =  Base64Utils.decode("bVlrMU9WV1dDa3QvNzZ2dXhpenBCdz09DQo=");
		str = DESUtil.decrypt(new String(bytes));
		
		before = System.currentTimeMillis();
		String s = DESUtil.decrypt("bW91ZTR5WjQwVkRlVDVESVJQK0ZpUT09DQo=");
		after = System.currentTimeMillis();
		System.out.println("解密数据为：");
		System.out.println(s);
		System.out.println("解密耗时："+ (after-before) + "ms");
		
		byte[] bytes1 = Base64Utils.decode("bW91ZTR5WjQwVkRlVDVESVJQK0ZpUT09DQo=");

		try {
			String target = new String(bytes1, "GBK");
			String password = DESUtil.decrypt(target);

			System.out.println(password);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
