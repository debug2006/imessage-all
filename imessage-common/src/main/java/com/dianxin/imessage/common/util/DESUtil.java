package com.dianxin.imessage.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;



/**
 * DES加解密工具
 * 
 * @author b_fatty
 * Date 2015/12/28
 *
 */
public class DESUtil {

	/*private static final String DES_KEY = 
			PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "des.key");*/
	
	private static final String DES_KEY = "paper1.0.0_app*#!DesKey";
	private static final String DES = "DES";
	
	private static String CHARSET = "UTF-8";
	
	/**
	 * 加密
	 * @author b_fatty
	 * @param data
	 * @return
	 */
	public static String encrypt(String data){
		String result = null;
		try {
			byte[] bt = encrypt(data.getBytes(CHARSET), DES_KEY.getBytes(CHARSET));
			result = Base64.encodeBase64String(bt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String decrypt(String data){
		String result = null;
		try {
			result = decrypt(Base64.decodeBase64(data), DES_KEY.getBytes(CHARSET));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @author b_fatty
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data,byte[] key) throws Exception{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}
	
	/**
	 * @author b_fatty
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static String decrypt(byte[] data,byte[] key) throws Exception{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return new String(cipher.doFinal(data));
	}
}
