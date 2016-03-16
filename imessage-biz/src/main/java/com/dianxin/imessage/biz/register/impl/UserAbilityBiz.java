package com.dianxin.imessage.biz.register.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.register.IUserAbilityBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxUserAbilityModel;
import com.dianxin.imessage.dao.mapper.UserAbilityMapper;

/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Mon Jan 18 19:57:57 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class UserAbilityBiz extends BaseBiz<DxUserAbilityModel> implements IUserAbilityBiz {

	@Autowired
	private UserAbilityMapper<DxUserAbilityModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxUserAbilityModel> getMapper() {
		return mapper;
	}
}
