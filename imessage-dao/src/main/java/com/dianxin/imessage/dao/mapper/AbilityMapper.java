package com.dianxin.imessage.dao.mapper;

import java.util.List;

import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxAbilityModel;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由Mapper映射文件来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:09:02 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface AbilityMapper<T> extends BaseMapper<T>{
	
	/**
	 * 根据uid查询超能力
	 */
	List<DxAbilityModel> queryAbilityByUid(Integer uid) throws Exception;
}
