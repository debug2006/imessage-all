package com.dianxin.imessage.biz.config;
       
import java.util.List;

import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxAbilityModel;
import com.dianxin.imessage.dao.dataobject.config.UserAbilityList;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由AbilityServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:09:02 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface IAbilityBiz  extends IBaseBiz<DxAbilityModel> {

	UserAbilityList getSuperPowerList(Integer uid);
	
	List<DxAbilityModel> getDxAbilityModelList(Integer uid) throws Exception;
	
}
