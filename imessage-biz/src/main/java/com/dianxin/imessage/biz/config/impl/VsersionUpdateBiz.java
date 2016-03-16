package com.dianxin.imessage.biz.config.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.config.IVsersionUpdateBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.VsersionUpdateMapper;

import com.dianxin.imessage.dao.dataobject.DxVsersionUpdateModel;
/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Fri Mar 11 12:53:56 CST 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class VsersionUpdateBiz extends BaseBiz<DxVsersionUpdateModel> implements IVsersionUpdateBiz {
	
	@Autowired
	private VsersionUpdateMapper<DxVsersionUpdateModel> mapper;


	/**
	 * 查询最新版本信息，进行对比
	 * @param version
	 * @param deviceType
	 * @return
	 */
	@Override
	public DxVsersionUpdateModel queryVersion(Integer deviceType) {
		try
		{
			DxVsersionUpdateModel dxVsersionUpdateModel = mapper.queryVersionByParm(deviceType);
			if(null!=dxVsersionUpdateModel)
			{
				return dxVsersionUpdateModel;
			}

		}catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @return
	 */
	public BaseMapper<DxVsersionUpdateModel> getMapper() {
		return mapper;
	}
}
