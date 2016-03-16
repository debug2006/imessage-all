package com.dianxin.imessage.biz.config.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.config.IUserAddrBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.mapper.UserAddrMapper;

import com.dianxin.imessage.dao.dataobject.DxUserAddrModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Thu Mar 10 00:34:53 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class UserAddrBiz extends BaseBiz<DxUserAddrModel> implements IUserAddrBiz {
	
	@Autowired
	private UserAddrMapper<DxUserAddrModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxUserAddrModel> getMapper() {
		return mapper;
	}

	@Override
	public boolean addRecord(DxUserAddrModel dxUserAddrModel) {
		int result = 0;
		try
		{
			mapper.insert(dxUserAddrModel);
			result = mapper.insert(dxUserAddrModel);

		}catch (Exception e)
		{
			e.printStackTrace();
		}

		return result==1?true:false;
	}

	@Override
	public boolean updateRecord(DxUserAddrModel dxUserAddrModel) {
		int result = 0;
		try
		{
			result = mapper.updateBySql("update tbl_user_addr set province = " + dxUserAddrModel.getProvince()
					+ " , city=" + dxUserAddrModel.getCity() +
					" , area=" + dxUserAddrModel.getArea() +
					" , addr_detail='"+dxUserAddrModel.getAddrDetail()+
					"' where addr_id="+dxUserAddrModel.getAddrId());
		}catch (Exception e)
		{
			e.printStackTrace();
		}

		return result==1?true:false;
	}

	@Override
	public List<DxUserAddrModel> queryList(Integer uid) {
		List<DxUserAddrModel> list = new ArrayList<DxUserAddrModel>();
		try {
			list = mapper.selectBySql("select * from tbl_user_addr");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
