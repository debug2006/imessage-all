package com.dianxin.imessage.biz.book.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.book.IUserInterestBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.UserInterestMapper;

import com.dianxin.imessage.dao.dataobject.DxUserInterestModel;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:37:27 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class UserInterestBiz extends BaseBiz<DxUserInterestModel> implements IUserInterestBiz {
	
	@Autowired
	private UserInterestMapper<DxUserInterestModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxUserInterestModel> getMapper() {
		return mapper;
	}
}
