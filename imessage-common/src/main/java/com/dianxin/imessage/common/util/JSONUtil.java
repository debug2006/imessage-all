package com.dianxin.imessage.common.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author b_fatty
 * Date: 2015/12/18
 *
 */

public class JSONUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * java对象转json
	 * @param o
	 * @return
	 */
	public static String toJSON(Object o){
		String json = null;
		try {
			json = objectMapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * json字符转java对象
	 * 
	 * @param t
	 * @return
	 */
	public static <T> T toObject (String json,Class<T> clazz){
		T t = null;
		try {
			t = objectMapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	
}
