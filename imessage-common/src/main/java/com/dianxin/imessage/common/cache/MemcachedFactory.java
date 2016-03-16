package com.dianxin.imessage.common.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.dianxin.imessage.common.exception.ExceptionMessageResolver;
import com.dianxin.imessage.common.exception.MemcachedException;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.spring.MemcachedClientFactoryBean;
/**
 * memcache工厂实现
 * @author b_fatty
 * Date 2016/1/8
 *
 */
public class MemcachedFactory implements CacheFactory{

	@Autowired
	private MemcachedClientFactoryBean memcachedClientFactoryBean;
	
	@Autowired
	private ExceptionMessageResolver emResolver;
	
	/**
	 * memcached客户端是单线程，每次都创建一个新的客户端
	 * @throws MemcachedException 
	 */
	@Override
	public Memcached createMemcached() throws MemcachedException {
		try {
			MemcachedClient client = (MemcachedClient)memcachedClientFactoryBean.getObject();
			return new Memcached(client);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new MemcachedException(emResolver.getExceptionMessage(MemcachedException.class),e);
		}
	}

}
