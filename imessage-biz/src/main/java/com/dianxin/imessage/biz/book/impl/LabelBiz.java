package com.dianxin.imessage.biz.book.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.book.ILabelBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.LabelMapper;

import com.dianxin.imessage.dao.dataobject.DxLabelModel;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Fri Mar 11 08:56:19 CST 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class LabelBiz extends BaseBiz<DxLabelModel> implements ILabelBiz {
	
	@Autowired
	private LabelMapper<DxLabelModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxLabelModel> getMapper() {
		return mapper;
	}
}
