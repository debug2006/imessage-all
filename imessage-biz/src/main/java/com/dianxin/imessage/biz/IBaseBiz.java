package com.dianxin.imessage.biz;

import com.dianxin.imessage.dao.base.BaseMapper;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由 BaseServiceImpl来实现通用的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> 2016/1/12 <br>
 * <b>版权所有：<b>dianxin.com<br>
 */
public interface IBaseBiz<T> extends BaseMapper<T> {
	/**
	 * 
	 * <br>
	 * <b>功能：</b>删除多条记录<br>
	 * <b>作者：</b>kai.fantasy<br>
	 * <b>日期：</b>2016/1/12<br>
	 * 
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrimaryKeys(Object... keys) throws Exception;
}
