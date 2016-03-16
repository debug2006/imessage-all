package com.dianxin.imessage.biz.register.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.register.IUserVersionBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxUserVersionModel;
import com.dianxin.imessage.dao.mapper.UserVersionMapper;

/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Tue Jan 19 00:11:13 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class UserVersionBiz extends BaseBiz<DxUserVersionModel> implements IUserVersionBiz {

	@Autowired
	private UserVersionMapper<DxUserVersionModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxUserVersionModel> getMapper() {
		return mapper;
	}
}
