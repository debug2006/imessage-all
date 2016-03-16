package com.dianxin.imessage.biz.chat;
       
import java.util.Map;

import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxUserselfExpressionModel;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由UserselfExpressionServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Mon Feb 22 23:25:40 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface IUserselfExpressionBiz  extends IBaseBiz<DxUserselfExpressionModel> {

	/**
	 * 保存表情
	 * @throws Exception 
	 */
	Integer saveExpression(DxUserselfExpressionModel expression) throws Exception;
	
	/**
	 * 删除表情
	 */
	void delExpressionByKey(Integer id) throws Exception;
	
	/**
	 * 获取表情列表
	 */
	Map<String,Object>[] getExpressionList(Integer uid) throws Exception;
}
