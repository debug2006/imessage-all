package com.dianxin.imessage.biz.config.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.common.oss.OSSConfigure;
import com.dianxin.imessage.common.oss.OSSManageUtil;
import com.dianxin.imessage.common.util.UserManageUtil;
import com.dianxin.imessage.biz.config.IUserBackgroundimgBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.UserBackgroundimgMapper;

import com.dianxin.imessage.dao.dataobject.DxUserBackgroundimgModel;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Mon Feb 22 23:11:09 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class UserBackgroundimgBiz extends BaseBiz<DxUserBackgroundimgModel> implements IUserBackgroundimgBiz {
	
	@Autowired
	private UserBackgroundimgMapper<DxUserBackgroundimgModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxUserBackgroundimgModel> getMapper() {
		return mapper;
	}

	@Override
	public Integer saveBackground(DxUserBackgroundimgModel background) throws Exception {
		
		return mapper.insert(background);
	}

	@Override
	@Transactional
	public void delBackground(Integer uid, Integer backgroundId) throws Exception {

		UserManageUtil.checkUid(uid);
		
		DxUserBackgroundimgModel model = mapper.selectByPrimaryKey(backgroundId);
		
		if(model != null){
			
			String path = model.getBackgroundPath();
			
			OSSConfigure ossConfigure = OSSConfigure.singleton();
			OSSManageUtil.deleteFile(ossConfigure, path);
			
			mapper.deleteByEntity(model);
		}
		
		
	}
}
