package com.dianxin.imessage.biz.config.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.config.IFeedbackBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.FeedbackMapper;

import com.dianxin.imessage.dao.dataobject.DxFeedbackModel;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:19:55 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class FeedbackBiz extends BaseBiz<DxFeedbackModel> implements IFeedbackBiz {
	
	@Autowired
	private FeedbackMapper<DxFeedbackModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxFeedbackModel> getMapper() {
		return mapper;
	}
}
