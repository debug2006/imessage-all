package com.dianxin.imessage.biz.chat.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.chat.IUserStatBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.UserStatMapper;

import com.dianxin.imessage.dao.dataobject.DxUserStatModel;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:39:25 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class UserStatBiz extends BaseBiz<DxUserStatModel> implements IUserStatBiz {
	
	@Autowired
	private UserStatMapper<DxUserStatModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxUserStatModel> getMapper() {
		return mapper;
	}

	@Override
	public DxUserStatModel getUserStatByUidAndFid(Integer uid, Integer fid) throws Exception {

		DxUserStatModel stat = null;
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("uid", uid);
		map.put("otherUid", fid);
		
		List<DxUserStatModel> stats = mapper.selectByMap(map);
		
		if(stats.size()>0){
			stat = stats.get(0);
		}
		
		return stat;
	}

	@Override
	public List<DxUserStatModel> getUserStatByUid(Integer uid) throws Exception {
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("uid", uid);
		return mapper.selectByMap(map);
	}
}
