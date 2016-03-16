package com.dianxin.imessage.biz.book.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.book.IReportBiz;
import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxReportModel;
import com.dianxin.imessage.dao.mapper.ReportMapper;

/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:34:25 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class ReportBiz extends BaseBiz<DxReportModel> implements IReportBiz {

	@Autowired
	private ReportMapper<DxReportModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxReportModel> getMapper() {
		return mapper;
	}

	@Override
	public Boolean addReportInfo(DxReportModel reportModel) {

		Boolean result = false;
		try {
			reportModel.setStatus(1);
			int insertResult = mapper.insert(reportModel);
			result = insertResult > 0 ? true : false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return result;
	}
}
