package com.dianxin.imessage.biz.config.impl;


import com.dianxin.imessage.biz.config.IActivityStatBiz;
import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxActivityStatModel;
import com.dianxin.imessage.dao.mapper.ActivityStatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Mon Mar 07 22:34:01 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class ActivityStatBiz extends BaseBiz<DxActivityStatModel> implements IActivityStatBiz {
	
	@Autowired
	private ActivityStatMapper<DxActivityStatModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxActivityStatModel> getMapper() {
		return mapper;
	}


	/**
	 * 记录打开次数
	 * @param uid
	 * @return
	 */
	public boolean addActivityTimes(Integer uid)
	{
		boolean flag = false;
		if(null==uid)
		{
			return flag;
		}
		DxActivityStatModel activityStatModel = new DxActivityStatModel();
		activityStatModel.setStatDate(new Date());
		activityStatModel.setUid(uid);

		try
		{
			List<DxActivityStatModel> list = selectBySql("select * from tbl_activity_stat where stat_date = CURDATE() and uid ="+uid);
			if(null==list||list.size()==0)
			{
				activityStatModel.setTimes(1);
				insert(activityStatModel);
			}
			else if(list.size()==1)
			{
				String sql = "update tbl_activity_stat set times = times + 1 where uid="+uid + " and stat_date = CURDATE()";
				updateBySql(sql);
			}
			flag = true;
		}catch (Exception e)
		{
			e.printStackTrace();
		}

		return flag;
	}
}
