package com.dianxin.imessage.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.IAppInfoBiz;
import com.dianxin.imessage.common.cache.MemcachedFactory;

@Service
public class AppInfoBiz implements IAppInfoBiz{

	@Autowired
	private MemcachedFactory cacheFactory;
	
	@Override
	public String getLateVer(String deviceType) {
		
		return null;
	}

}
