package com.dianxin.imessage.biz.config.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.config.IAbilityBiz;
import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.register.IUserAbilityBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxAbilityModel;
import com.dianxin.imessage.dao.dataobject.DxUserAbilityModel;
import com.dianxin.imessage.dao.dataobject.config.UserAbility;
import com.dianxin.imessage.dao.dataobject.config.UserAbilityList;
import com.dianxin.imessage.dao.mapper.AbilityMapper;

/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:09:02 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class AbilityBiz extends BaseBiz<DxAbilityModel> implements IAbilityBiz {

	@Autowired
	private AbilityMapper<DxAbilityModel> mapper;

	@Autowired
	private IUserAbilityBiz userAbilityBiz;

	/**
	 * @return
	 */
	public BaseMapper<DxAbilityModel> getMapper() {
		return mapper;
	}

	@Override
	public UserAbilityList getSuperPowerList(Integer uid) {

		UserAbilityList userAbilityList = new UserAbilityList();

		try {

			List<UserAbility> userAbilitys = null;

			List<DxAbilityModel> allAbilityModel = getDxAbilityModelList(uid);

			if (null == allAbilityModel) {
				return null;
			}

			userAbilitys = new ArrayList<UserAbility>();
			for (DxAbilityModel abilityModel : allAbilityModel) {
				UserAbility userAbility = new UserAbility();
				userAbility.setAbilityId(abilityModel.getId());
				userAbility.setAbilityName(abilityModel.getAbilityName());
				userAbility.setAbilityDesc(abilityModel.getAbilityDesc());
				userAbilitys.add(userAbility);
			}

			userAbilityList.setTotal(userAbilitys.size());
			userAbilityList.setUserAbilityList(userAbilitys);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userAbilityList;
	}

	/**
	 * 根据UID 查询 能力列表， 如果uid为空，则返全部超能力信息
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DxAbilityModel> getDxAbilityModelList(Integer uid) throws Exception {

		List<DxAbilityModel> allAbilityModel = null;

		if (null == uid) {
			// uid 为空，则获取全部超能力信息
			allAbilityModel = mapper.selectByEntiry(new DxAbilityModel());
		} else {
			DxUserAbilityModel userAbility = new DxUserAbilityModel();
			userAbility.setUid(uid);
			List<DxUserAbilityModel> userAbilityModels = userAbilityBiz.selectByEntiry(userAbility);

			allAbilityModel = new ArrayList<DxAbilityModel>();
			for (DxUserAbilityModel userAbilityResult : userAbilityModels) {
				DxAbilityModel abilityModel = mapper.selectByPrimaryKey(userAbilityResult.getAbiliyId());
				allAbilityModel.add(abilityModel);
			}
		}

		return allAbilityModel;
	}
}
