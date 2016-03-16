package com.dianxin.imessage.common.cache;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.spring.MemcachedClientFactoryBean;

/**
 * memcache客户端实现
 * 
 * @author b_fatty
 * Data 2016/1/8
 *
 */
public class Memcached implements Cache{

	private MemcachedClient client;
	
	protected Memcached(MemcachedClient client){
		this.client = client;
	}
	
	@Override
	public boolean add(String key, Object value, int exp) {
		OperationFuture<Boolean> of = client.add(key, exp, value);
		try {
			return of.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean add(String key, Object value) {
		return add(key,value,Cache.MEMCACHE_DEFAULT_EXP);
	}
	
	@Override
	public boolean set(String key, Object value) {
		return set(key,value,Cache.MEMCACHE_DEFAULT_EXP);
	}
	
	@Override
	public boolean set(String key, Object value, int exp) {
		OperationFuture<Boolean> of = client.set(key, exp, value);
		try {
			return of.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Object get(String key){
		return client.get(key);
	}
	
	@Override
	public boolean delete(String key){
		OperationFuture<Boolean> of = client.delete(key);
		try {
			return of.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}
