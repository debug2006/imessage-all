package com.dianxin.imessage.common.cache;

public interface Cache {

	//memcache数据默认永不过期
	public static int MEMCACHE_DEFAULT_EXP = 0;
	
	boolean add(String key,Object value,int exp);
	
	boolean add(String key,Object value);
	
	boolean set(String key,Object value,int exp);
	
	boolean set(String key,Object value);
	
	Object get(String key);
	
	boolean delete(String key);
	
}
