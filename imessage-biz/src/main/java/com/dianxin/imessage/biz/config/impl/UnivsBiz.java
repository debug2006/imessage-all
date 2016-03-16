package com.dianxin.imessage.biz.config.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.config.IUnivsBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.UnivsMapper;

import com.dianxin.imessage.dao.dataobject.DxUnivsModel;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Thu Mar 10 00:43:14 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class UnivsBiz extends BaseBiz<DxUnivsModel> implements IUnivsBiz {
	
	@Autowired
	private UnivsMapper<DxUnivsModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxUnivsModel> getMapper() {
		return mapper;
	}
}
