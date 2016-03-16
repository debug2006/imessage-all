package com.dianxin.imessage.biz.book;
       
import java.util.List;
import java.util.Map;

import com.dianxin.imessage.biz.IBaseBiz;
import com.dianxin.imessage.dao.dataobject.DxInterestModel;
import com.dianxin.imessage.dao.dataobject.book.UserInterestList;

/**
 * 
 * <br>
 * <b>功能：</b>定义在这里由InterestServiceImp来实现 私有的<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 23 21:24:05 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
public interface IInterestBiz  extends IBaseBiz<DxInterestModel> {

	/**
	 * 根据uid查找用户的兴趣
	 */
	List<DxInterestModel> getInterestByUid(Integer uid) throws Exception;
	
	
	UserInterestList getInterestList(Integer uid);
	
	/**
	 * 根据uid和fid计算两个用户有多少个相同的兴趣
	 */
	Integer commonInterestNum(Integer uid , Integer fid) throws Exception;
	
	/**
	 * 根据uid和fid获取两个用户相同的兴趣，用,号分割
	 */
	String commonInterestName(Integer uid , Integer fid) throws Exception;
	
	/**
	 * 根据条件模糊查询兴趣，并关联出相同标签的兴趣
	 */
	Map<String,?>[] searchInteres(String interestName,Integer pagesize,Integer pageindex) throws Exception;

	/**
	 * 用户保存兴趣
	 */
	void saveUserInterest(Integer uid, Integer interestId) throws Exception;
	
	/**
	 * 增加用户上传的兴趣
	 */
	void saveInterestAudit(Integer uid,String interestName) throws Exception;
}
