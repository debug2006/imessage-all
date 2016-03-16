package com.dianxin.imessage.biz.book.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.book.IInterestLabelBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.InterestLabelMapper;

import com.dianxin.imessage.dao.dataobject.DxInterestLabelModel;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Fri Mar 11 08:55:39 CST 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class InterestLabelBiz extends BaseBiz<DxInterestLabelModel> implements IInterestLabelBiz {
	
	@Autowired
	private InterestLabelMapper<DxInterestLabelModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxInterestLabelModel> getMapper() {
		return mapper;
	}
}
