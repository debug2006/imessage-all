package com.dianxin.imessage.biz.book;
       
import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxReportModel;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由ReportServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:34:25 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface IReportBiz  extends IBaseBiz<DxReportModel> {

	Boolean addReportInfo(DxReportModel reportModel);
	
}
