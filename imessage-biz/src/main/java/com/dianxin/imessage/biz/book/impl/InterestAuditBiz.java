package com.dianxin.imessage.biz.book.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.book.IInterestAuditBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.InterestAuditMapper;

import com.dianxin.imessage.dao.dataobject.DxInterestAuditModel;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Thu Mar 10 00:27:13 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class InterestAuditBiz extends BaseBiz<DxInterestAuditModel> implements IInterestAuditBiz {
	
	@Autowired
	private InterestAuditMapper<DxInterestAuditModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxInterestAuditModel> getMapper() {
		return mapper;
	}
}
