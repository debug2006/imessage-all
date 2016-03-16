package com.dianxin.imessage.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class JSONUtilTest {

	@Test
	public void testToJson(){
		Test1 t= new Test1();
		t.map = new HashMap<String,Object>();
		t.map.put("name", "111");
		System.out.println(JSONUtil.toJSON(t));
	}
	
	@Test
	public void testToObject(){
		String json = "{\"map\":{\"name1\":\"22222\"},\"name\":\"test\",\"number\":3}";
		
		Test1 t = JSONUtil.toObject(json, Test1.class);
		System.out.println(t.name);
		System.out.println(t.number);
	}
}
class Test1{
	public Map map = new HashMap();
	public String name = "test";
	public int number = 3;
	
	public Map getMap(){
		return this.map;
	}
}