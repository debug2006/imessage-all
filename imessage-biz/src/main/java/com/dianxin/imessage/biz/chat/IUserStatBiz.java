package com.dianxin.imessage.biz.chat;
       
import java.util.List;

import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxUserStatModel;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由UserStatServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:39:25 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface IUserStatBiz  extends IBaseBiz<DxUserStatModel> {

	/**
	 * 根据uid和fid获取stat对象
	 * @throws Exception 
	 */
	DxUserStatModel getUserStatByUidAndFid(Integer uid,Integer fid) throws Exception;
	
	List<DxUserStatModel> getUserStatByUid(Integer uid) throws Exception;
}
