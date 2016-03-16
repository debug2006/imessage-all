package com.dianxin.imessage.common.cache;

import com.dianxin.imessage.common.exception.MemcachedException;

/**
 * 缓存工具工厂
 * @author b_fatty
 * Date 2016/1/8
 *
 */
public interface CacheFactory {

	Cache createMemcached() throws MemcachedException;
}
