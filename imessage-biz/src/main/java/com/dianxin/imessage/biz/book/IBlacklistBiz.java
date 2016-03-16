package com.dianxin.imessage.biz.book;

import com.dianxin.imessage.dao.dataobject.book.MyBlackList;

/**
 * 
 * <br>
 * <b>功能：</b>通讯录-黑名单管理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> 20160131 <br>
 */
public interface IBlacklistBiz {

	/**
	 * 
	 * 设置黑名单信息
	 * 
	 * @param uid
	 * @param blackUid
	 * @param isSet
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	Boolean setBlacklist(Integer uid, String blackUid, Boolean isSet);

	/**
	 * 
	 * 获取黑名单列表
	 * 
	 * @param uid
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	MyBlackList getBlackList(String uid);

	/**
	 * 
	 * 更新黑名单信息
	 * 
	 * @param myBlackList
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	String updateBlackList(MyBlackList myBlackList);

}
