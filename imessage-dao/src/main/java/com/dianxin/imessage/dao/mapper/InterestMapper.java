package com.dianxin.imessage.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxInterestModel;	

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由Mapper映射文件来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:24:05 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface InterestMapper<T> extends BaseMapper<T>{
	
	/**
	 * 根据uid查询兴趣
	 */
	List<DxInterestModel> queryInterestByUid(@Param(value = "uid")Integer uid) throws Exception;

	Integer queryCommonInterestByUidAndFid(Map<?,?> map) throws Exception;
	
	List<DxInterestModel> queryCommonInterest(Map<?,?> map) throws Exception;
	
	/**
	 * 搜索兴趣
	 */
	List<DxInterestModel> searchInterest(Map<?,?> map)throws Exception;
}
