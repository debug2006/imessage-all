package com.dianxin.imessage.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxUserModel;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由Mapper映射文件来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> 2016/1/12 <br>
 * <b>版权所有：<b>dianxin.com<br>
 */
public interface UserMapper<T> extends BaseMapper<T> {
	
	public Integer existUid(@Param(value = "uid") Integer uid) throws Exception;	

	public Integer existUserGenNum(@Param(value = "userGenNum") String userGenNum) throws Exception;
	
	public Integer existPhone(@Param(value = "phone") Long phone) throws Exception;

	public DxUserModel queryUserInfoByMap(Map<?, ?> map) throws Exception;

	/**
	 * 根据手机号数组获取对应的用户 by b_fatty
	 */
	List<T> queryUsersByPhones(@Param(value = "phones")List<String> phones) throws Exception;
	
	/**
	 * 根据uid集合获取一组user
	 */
	List<T> queryUsersByUids(Map<?,?> map)throws Exception;
	
	List<T> queryUsersByPhoneOrUsernum(@Param(value="param")String param) throws Exception;
}
