package com.dianxin.imessage.biz.config;
       
import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxUserBackgroundimgModel;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由UserBackgroundimgServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Mon Feb 22 23:11:09 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface IUserBackgroundimgBiz  extends IBaseBiz<DxUserBackgroundimgModel> {

	/**
	 * 保存背景
	 */
	Integer saveBackground(DxUserBackgroundimgModel background) throws Exception;
	
	/**
	 * 清除背景
	 */
	void delBackground(Integer uid, Integer backgroundId) throws Exception;
}
