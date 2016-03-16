package com.dianxin.imessage.biz.book.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.book.IInterestAuditBiz;
import com.dianxin.imessage.biz.book.IInterestBiz;
import com.dianxin.imessage.biz.book.IUserInterestBiz;
import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.common.util.UserManageUtil;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxInterestAuditModel;
import com.dianxin.imessage.dao.dataobject.DxInterestModel;
import com.dianxin.imessage.dao.dataobject.DxUserInterestModel;
import com.dianxin.imessage.dao.dataobject.book.UserInterest;
import com.dianxin.imessage.dao.dataobject.book.UserInterestList;
import com.dianxin.imessage.dao.mapper.InterestMapper;

/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:24:05 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class InterestBiz extends BaseBiz<DxInterestModel> implements IInterestBiz {

	@Autowired
	private InterestMapper<DxInterestModel> mapper;

	@Autowired
	private IUserInterestBiz userInterestBiz;
	
	@Autowired
	private IInterestAuditBiz interestAuditBiz;

	/**
	 * @return
	 */
	public BaseMapper<DxInterestModel> getMapper() {
		return mapper;
	}

	@Override
	public List<DxInterestModel> getInterestByUid(Integer uid) throws Exception {

		return mapper.queryInterestByUid(uid);
	}

	/**
	 * 
	 */
	@Override
	public UserInterestList getInterestList(Integer uid) {

		UserInterestList userInterestList = new UserInterestList();

		List<UserInterest> userInterests = null;

		try {
			List<DxInterestModel> interestModels = getInterestModel(uid);

			if (null == interestModels) {
				return null;
			}

			userInterests = new ArrayList<UserInterest>();

			for (DxInterestModel interestModel : interestModels) {
				UserInterest userInterest = new UserInterest();
				userInterest.setUid(uid);
				userInterest.setInterest(interestModel.getInterest());
				userInterests.add(userInterest);
			}

			userInterestList.setTotal(userInterests.size());
			userInterestList.setUserInterestList(userInterests);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInterestList;
	}

	/**
	 * 根据UID 获取 兴趣列表
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	private List<DxInterestModel> getInterestModel(Integer uid) throws Exception {

		List<DxInterestModel> interestModel = null;

		if (null == uid) {
			interestModel = mapper.selectByEntiry(new DxInterestModel());
		} else {
			DxUserInterestModel userInterest = new DxUserInterestModel();
			userInterest.setUid(uid);
			List<DxUserInterestModel> userInterestModels = userInterestBiz.selectByEntiry(userInterest);
			interestModel = new ArrayList<DxInterestModel>();

			for (DxUserInterestModel userInterestModel : userInterestModels) {
				DxInterestModel interest = mapper.selectByPrimaryKey(userInterestModel.getInterestId());
				interestModel.add(interest);
			}
		}
		return interestModel;
	}

	@Override
	public Integer commonInterestNum(Integer uid, Integer fid) throws Exception {
		
		/*UserManageUtil.checkUid(uid);
		UserManageUtil.checkUid(fid);*/
		
		Map<String ,Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("uid", uid);
		paramMap.put("fid", fid);
		
		return mapper.queryCommonInterestByUidAndFid(paramMap);
		
	}

	@Override
	public String commonInterestName(Integer uid, Integer fid) throws Exception {
		
		/*UserManageUtil.checkUid(uid);
		UserManageUtil.checkUid(fid);*/
		String result = null;
		
		Map<String ,Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("uid", uid);
		paramMap.put("fid", fid);
		
		List<DxInterestModel> list =  mapper.queryCommonInterest(paramMap);
		
		if(list!=null && list.size()>0){
			for(DxInterestModel i : list){
				if(result==null){
					result = i.getInterest();
				}else{
					result = result + "," + i.getInterest();
				}
			}
		}
		
		return result;
	}

	@Override
	public Map<String,?>[] searchInteres(String interestName, Integer pagesize, Integer pageindex)
			throws Exception {

		Map<String ,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("interestName", interestName);
		paramMap.put("pageOffset", (pageindex-1)*pagesize);
		paramMap.put("pageSize", pagesize);
		
		List<DxInterestModel> list =  mapper.searchInterest(paramMap);
		
		Map<String,?>[] maps = null;
		
		if(null!=list && list.size()>0){
			maps = new HashMap[list.size()];
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("interestId", list.get(i).getId());
				map.put("interestName", list.get(i).getInterest());
			}
		}
		return maps;
	}

	@Override
	public void saveUserInterest(Integer uid, Integer interestId) throws Exception {
		
		DxUserInterestModel userInterest = new DxUserInterestModel();
		userInterest.setUid(uid);
		userInterest.setInterestId(interestId);
		List<DxUserInterestModel> userInterestModels = userInterestBiz.selectByEntiry(userInterest);
		
		/**
		 * 表中没有数据则插入数据
		 */
		if(null == userInterestModels||userInterestModels.size()==0){
			userInterestBiz.insert(userInterest);
		}
	}

	@Override
	public void saveInterestAudit(Integer uid, String interestName) throws Exception {

		UserManageUtil.checkUid(uid);
		
		DxInterestAuditModel interestAuditModel = new DxInterestAuditModel();
		interestAuditModel.setInterest(interestName);
		List<DxInterestAuditModel>  list= interestAuditBiz.selectByEntiry(interestAuditModel);
		
		//没有数据则插入
		if(list == null || list.size() ==0){
			interestAuditModel.setUid(uid);
			interestAuditModel.setAuditStatus(1);
			interestAuditBiz.insert(interestAuditModel);
		}
	}
}
