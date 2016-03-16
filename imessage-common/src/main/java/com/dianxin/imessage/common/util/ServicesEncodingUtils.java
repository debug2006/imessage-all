package com.dianxin.imessage.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author kai.fantasy
 *
 */
public class ServicesEncodingUtils {

	public static String encrypt(String sourceString) {
		return encodePassword(sourceString, "MD5");
	}

	/**
	 * Use the key to encrypt the source string by MD5
	 * 
	 * @param sourceString
	 * @param key
	 *            defind by yourself
	 * @return
	 */
	public static String encrypt(String sourceString, String key) {
		return encodePassword(sourceString + key, "MD5");
	}

	public static String encryptRequestParam(String requestParam, String key) {
		String[] strArr = requestParam.toString().split("&");
		Arrays.sort(strArr);
		String newParams = StringUtils.join(strArr, "&");
		return encodePassword(newParams + key, "MD5");
	}

	public static String encryptRequestParam(Map requestParam, String key) {
		String[] strArr = new String[requestParam.size()];
		Iterator itMapKey = requestParam.keySet().iterator();
		StringBuffer requestParamStr = new StringBuffer();
		int i = 0;
		while (itMapKey.hasNext()) {
			String mapKey = (String) itMapKey.next();
			Object vObject = requestParam.get(mapKey);
			String value = "";
			if (vObject instanceof String[]) {
				value = ((String[]) vObject)[0];
			} else {
				value = vObject.toString();
			}
			requestParamStr.append(mapKey).append("=").append(value);

			strArr[i] = requestParamStr.toString();
			requestParamStr = new StringBuffer();
			i++;
		}
		Arrays.sort(strArr);
		String newParams = StringUtils.join(strArr, "&");
		return encodePassword(newParams + key, "MD5");
	}

	/**
	 * Encodes the rawPass using a MessageDigest. If a salt is specified it will
	 * be merged with the password before encoding.
	 *
	 * @param rawPass
	 *            The plain text password
	 * @param salt
	 *            The salt to sprinkle
	 * @return Hex string of password digest (or base64 encoded string if
	 *         encodeHashAsBase64 is enabled.
	 */
	private static String encodePassword(String rawPass, String algorithm) {

		MessageDigest messageDigest = getMessageDigest(algorithm);

		byte[] digest;

		try {
			digest = messageDigest.digest(rawPass.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 not supported!");
		}

		return new String(Hex.encodeHex(digest));
	}

	/**
	 * Get a MessageDigest instance for the given algorithm. Throws an
	 * IllegalArgumentException if <i>algorithm</i> is unknown
	 *
	 * @return MessageDigest instance
	 * @throws IllegalArgumentException
	 *             if NoSuchAlgorithmException is thrown
	 */
	private static MessageDigest getMessageDigest(String algorithm) throws IllegalArgumentException {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
		}
	}
}
