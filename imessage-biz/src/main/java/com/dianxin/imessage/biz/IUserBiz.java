package com.dianxin.imessage.biz;

import com.dianxin.imessage.common.xmpp.XmppManager;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.dataobject.book.FriendsBirthdayList;

import java.util.List;
import java.util.Map;

public interface IUserBiz extends IBaseBiz<DxUserModel> {

	/**
	 * 从连接池中获取xmppmanager对象，如果没有创建一个新的放进去
	 * 
	 * @throws Exception
	 */
	XmppManager getXmppManagerForPool(Integer uid) throws Exception;

	/**
	 * 从连接池中获取xmppmanager对象，如果没有创建一个新的放进去
	 * 
	 * @throws Exception
	 */
	XmppManager getXmppManagerForPool(Integer uid, String password) throws Exception;

	/**
	 * 根据手机号判断是否为纸条君用户
	 * 
	 */
	Map<String, Object>[] isZTJUser(String[] phones) throws Exception;

	/**
	 * 判断是否为好友
	 * 
	 * @throws Exception
	 */
	boolean isFirend(Integer uid, Integer fid) throws Exception;

	/**
	 * 申请加为好友
	 */
	void inviteFriend(Integer uid, Integer fid, String message, String groupName, String nickName) throws Exception;

	/**
	 * 处理好友申请的请求
	 */
	void processInvite(Integer uid, Integer fid) throws Exception;

	/**
	 * 跟着纸条号获取用户信息
	 * 
	 * @throws Exception
	 */
	DxUserModel getUserInfoByUserNum(String userNum) throws Exception;

	/**
	 * 根据纸条号或电话号码搜索用户
	 * 
	 * 前端需求返回多条数据 暂时废弃
	 */
	@Deprecated
	DxUserModel searchUserByUserNumOrPhone(String param) throws Exception;

	List<DxUserModel> searchUserByUserNumOrPhone_(String param) throws Exception;

	/**
	 * 根据uid获取user对象
	 */
	DxUserModel getUserByKey(Integer uid) throws Exception;

	/**
	 * 修改密码
	 */
	void changePwd(Long phone, String password) throws Exception;

	/**
	 * 获取好友列表
	 */
	Map<String, Object>[] getFirendList(Integer uid, Integer pageSize, Integer pageIndex) throws Exception;

	/**
	 * 获取好友数量
	 */
	Integer getFirendsNum(Integer uid) throws Exception;


	/**
	 * 
	 * 是否可以修改bluId
	 * 
	 * @param uid
	 * @return
	 * 
	 */
	Boolean canModifyUserNum(Integer uid);

	/**
	 * 
	 * 修改用户信息
	 * 
	 * @param userModel
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	Boolean modifyUserInfo(DxUserModel userModel);

	/**
	 * 
	 * 更新个人资料完善程度
	 * 
	 * @param uid
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	Boolean modifyUserInfoCompletenes(Integer uid);

	/**
	 * 
	 * 设置好友生日提醒
	 * 
	 * @param uid
	 * @param fUid
	 * @param isSet
	 *            是否设置提醒
	 * @return
	 */
	Boolean setBirthdayReminderList(Integer uid, String fUid, Boolean isSet);

	/**
	 * 
	 * 获取当天过生日的好友的列表(设置过好友生日提醒的)
	 * 
	 * @param uid
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	FriendsBirthdayList getFriendsBirthdayList(Integer uid);
}
