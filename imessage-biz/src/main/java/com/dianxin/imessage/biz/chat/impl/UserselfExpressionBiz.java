package com.dianxin.imessage.biz.chat.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.common.util.UserManageUtil;
import com.dianxin.imessage.biz.chat.IUserselfExpressionBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.UserselfExpressionMapper;

import com.dianxin.imessage.dao.dataobject.DxUserselfExpressionModel;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Mon Feb 22 23:25:40 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class UserselfExpressionBiz extends BaseBiz<DxUserselfExpressionModel> implements IUserselfExpressionBiz {
	
	@Autowired
	private UserselfExpressionMapper<DxUserselfExpressionModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxUserselfExpressionModel> getMapper() {
		return mapper;
	}

	@Override
	public Integer saveExpression(DxUserselfExpressionModel expression) throws Exception {

		return mapper.insert(expression);
	}

	@Override
	public void delExpressionByKey(Integer id) throws Exception {
		
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public Map<String, Object>[] getExpressionList(Integer uid) throws Exception {
		
		UserManageUtil.checkUid(uid);
		
		DxUserselfExpressionModel entity = new DxUserselfExpressionModel();
		entity.setUid(uid);
		List<DxUserselfExpressionModel> list = mapper.selectByEntiry(entity);
		
		if(null!=list&&list.size()>0){
			
			Map<String, Object>[] maps = new HashMap[list.size()];
			
			for(int i=0; i<list.size() ;i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("expressionId", list.get(i).getId());
				map.put("path", list.get(i).getFilePath());
				maps[i] = map;
			}
			
			return maps;
		}
		
		return null;
	}
}
