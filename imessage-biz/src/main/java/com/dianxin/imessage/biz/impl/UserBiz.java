package com.dianxin.imessage.biz.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.book.ILabelManageBiz;
import com.dianxin.imessage.biz.config.IUserImageBiz;
import com.dianxin.imessage.common.cache.Memcached;
import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.exception.userManager.UserException;
import com.dianxin.imessage.common.util.DateUtil;
import com.dianxin.imessage.common.util.ServicesEncodingUtils;
import com.dianxin.imessage.common.util.UserManageUtil;
import com.dianxin.imessage.common.util.ValidatorTools;
import com.dianxin.imessage.common.xmpp.XmppManager;
import com.dianxin.imessage.common.xmpp.XmppManagerPool;
import com.dianxin.imessage.common.xmpp.extend.InviteFriendExtension;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxUserImageModel;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.dataobject.book.FriendInfo;
import com.dianxin.imessage.dao.dataobject.book.FriendsBirthdayList;
import com.dianxin.imessage.dao.dataobject.book.TagInfo;
import com.dianxin.imessage.dao.mapper.UserMapper;

/**
 * 
 * @author b_fatty
 * 
 *         用户业务层
 *
 */
@Service
public class UserBiz extends BaseBiz<DxUserModel> implements IUserBiz {

	private static Logger logger = LoggerFactory.getLogger(UserBiz.class);

	// 从配置中获取
	private String BIRTHDAYREMINDERLIST = "sys_Birthday_Reminders";

	@Autowired
	private XmppManagerPool xmppManagerPool;

	@Autowired
	private UserMapper<DxUserModel> mapper;

	@Autowired
	private MemcachedFactory memcachedFactory;

	@Autowired
	private IUserImageBiz userImageBiz;

	@Autowired
	private ILabelManageBiz labelManageBiz;

	/**
	 * @return
	 */
	public BaseMapper<DxUserModel> getMapper() {
		return mapper;
	}

	@Override
	public XmppManager getXmppManagerForPool(Integer uid) throws Exception {

		// UserManageUtil.checkUid(uid); //验证uid是否符合要求

		XmppManager manager = xmppManagerPool.get(uid); // 从池中获取

		if (null == manager) { // 如果池中没有 创建一个新的并放到池中

			/*
			 * DxUserModel user = mapper.selectByPrimaryKey(uid);
			 * 
			 * if (null == user) { // TODO 自定义用户不存在异常 }
			 */

			Memcached memcache = memcachedFactory.createMemcached();

			String password = (String) memcache.get(uid + "_pwd");

			if (StringUtils.isEmpty(password)) {

				throw new UserException("not found password at memcache", ErrCodeEnum.NOT_FOUND_PWD_MEM.value);
			}

			manager = xmppManagerPool.loadXmppManager(uid, password);
		}

		return manager;
	}

	@Override
	public Map<String, Object>[] isZTJUser(String[] phones) throws Exception {

		if (null != phones && phones.length > 0) {
			// TDDO 抛出自定义异常
		}

		Map<String, Object>[] result = new Map[phones.length];

		List<DxUserModel> users = mapper.queryUsersByPhones(Arrays.asList(phones));

		for (int i = 0; i < phones.length; i++) { // 号码是否在结果集中
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("telphone", phones[i]);
			map.put("userType", 2);
			map.put("userNum", null);
			map.put("headPhoto", null);
			map.put("userName", null);
			for (DxUserModel user : users) {
				if (Long.valueOf(phones[i]).equals(user.getTelphone())) { // 确定是纸条君用户
					map.put("uid", user.getUid());
					map.put("userType", 1);
					map.put("userNum",
							StringUtils.isEmpty(user.getUserNum()) ? user.getUserNum() : user.getUserGenNum());
					map.put("userName", user.getUserName());
					DxUserImageModel image = userImageBiz.getUserImage(user.getUid());
					if (image != null) {
						map.put("headPhoto", image.getFilePath());
					}
					break;
				}
			}
			result[i] = map;
		}

		return result;
	}

	@Override
	public boolean isFirend(Integer uid, Integer fid) throws Exception {

		/*
		 * UserManageUtil.checkUid(uid); //验证uid是否符合要求
		 * UserManageUtil.checkUid(fid);
		 */

		XmppManager manager = getXmppManagerForPool(uid);

		RosterEntry entry = manager.getEntry(fid);

		if (entry != null) {
			return true;
		}

		return false;
	}

	@Override
	public void inviteFriend(Integer uid, Integer fid, String message, String groupName, String nickName)
			throws Exception {

		UserManageUtil.checkUid(uid); // 验证uid是否符合要求
		UserManageUtil.checkUid(fid);

		DxUserModel user = mapper.selectByPrimaryKey(uid);
		if (user == null) {
			throw new UserException("[" + uid + "]user not exist", ErrCodeEnum.USER_NOT_EXIST.value);
		}

		XmppManager manager = getXmppManagerForPool(uid);

		Memcached memcache = memcachedFactory.createMemcached();

		DxUserImageModel image = userImageBiz.getUserImage(uid);

		// 附加信息
		String cacheId = "IF_" + fid + "_" + uid;
		String cacheValue = message + "&" + user.getUserName() + "&" + (image == null ? null : image.getFilePath());

		// 分组和昵称
		String cacheGId = "IF_G_" + fid + "_" + uid;
		String cacheGValue = groupName + "&" + nickName;

		// 时间
		String cacheTId = "IF_T_" + fid + "_" + uid;
		String cacheTValue = DateUtil.DateToString(new Date(), "yyyy-MM-dd");

		memcache.add(cacheId, cacheValue);
		memcache.add(cacheGId, cacheGValue);
		memcache.add(cacheTId, cacheTValue);

		List<ExtensionElement> extensions = new ArrayList<ExtensionElement>();
		InviteFriendExtension extension = new InviteFriendExtension();

		extension.setAttachInfo(message);
		extension.setUserName(user.getUserName());
		if (image != null) {
			extension.setHeadPath(image.getFilePath());
		}

		extensions.add(extension);

		manager.sendPacket(Presence.Type.subscribe, fid, extensions); // 发送好友请求数据包

	}

	@Override
	public void processInvite(Integer uid, Integer fid) throws Exception {

		UserManageUtil.checkUid(uid); // 验证uid是否符合要求
		UserManageUtil.checkUid(fid);

		XmppManager manager = getXmppManagerForPool(uid);

		manager.sendPacket(Presence.Type.subscribed, fid); // 发送同意添加数据包
	}

	@Override
	public DxUserModel getUserInfoByUserNum(String userNum) throws Exception {
		// TODO 验证纸条号格式

		Map<String, String> map = new HashMap<String, String>();
		map.put("user_num", userNum);

		return mapper.queryUserInfoByMap(map);
	}

	@Override
	@Deprecated
	public DxUserModel searchUserByUserNumOrPhone(String param) throws Exception {

		Map<String, Object> map = null;

		if (ValidatorTools.isMobile(param)) { // 通过电话搜索
			map = new HashMap<String, Object>();
			map.put("telphone", param);
			map.put("searchTelFalg", 1);
			map.put("isDel", 1);
		} else /* if (ValidatorTools.isUsername(param)) */ { // 通过纸条号搜索
			map = new HashMap<String, Object>();
			map.put("userNum", param);
			map.put("searchNumFalg", 1);
			map.put("isDel", 1);
		}

		if (map != null) {
			List<DxUserModel> list = mapper.selectByMap(map);
			if (list.size() > 0) {
				return list.get(0);
			}
		}

		return null;
	}

	@Override
	public List<DxUserModel> searchUserByUserNumOrPhone_(String param) throws Exception {

		return mapper.queryUsersByPhoneOrUsernum(param);
	}

	@Override
	public DxUserModel getUserByKey(Integer uid) throws Exception {

		return mapper.selectByPrimaryKey(uid);
	}

	@Override
	public void changePwd(Long phone, String password) throws Exception {

		// 密码进行MD5加密,加密密匙，从数据库配置表中获取
		// String dxpasswd = ServicesEncodingUtils.encrypt(password, "Dx2016");
		String dxpasswd = ServicesEncodingUtils.encrypt(password);

		DxUserModel model = new DxUserModel();
		model.setTelphone(phone);
		model.setPassword(dxpasswd);

		mapper.updateByPrimaryKey(model);

	}

	@Override
	public XmppManager getXmppManagerForPool(Integer uid, String password) throws Exception {
		// UserManageUtil.checkUid(uid); //验证uid是否符合要求

		XmppManager manager = xmppManagerPool.get(uid); // 从池中获取

		if (null == manager) { // 如果池中没有 创建一个新的并放到池中

			DxUserModel user = mapper.selectByPrimaryKey(uid);

			if (null == user) {
				// TODO 自定义用户不存在异常
			}

			manager = xmppManagerPool.loadXmppManager(uid, password);
		}

		return manager;
	}

	@Override
	public Map<String, Object>[] getFirendList(Integer uid, Integer pageSize, Integer pageIndex) throws Exception {

		XmppManager manager = xmppManagerPool.get(uid); // 从池中获取

		List<RosterEntry> entres = manager.getAllEntries();

		if (null != entres && entres.size() > 0) {

			List<Integer> uids = new ArrayList<Integer>();

			for (RosterEntry entry : entres) {
				uids.add(XmppManager.getUserUid(entry));
			}

			if (uids.size() > 0) {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("uids", uids);
				map.put("pageOffset", (pageIndex - 1) * pageSize);
				map.put("pageSize", pageSize);

				List<DxUserModel> users = mapper.queryUsersByUids(map);

				Map<String, Object>[] results = new Map[users.size()];

				for (int i = 0; i < users.size(); i++) {

					RosterEntry entry = manager.getEntry(users.get(i).getUid());
					if (entry == null) {
						throw new UserException("not openfire firend", ErrCodeEnum.NOT_OPENFIRE_FIREND.value);
					}

					Map<String, Object> result = new HashMap<String, Object>();
					result.put("uid", users.get(i).getUid());
					result.put("userNum", users.get(i).getUserNum());
					result.put("userName", users.get(i).getUserName());
					result.put("phone",  users.get(i).getTelphone());
					result.put("remackName", entry.getName());

					results[i] = result;
				}

				return results;
			}

		}

		return null;
	}

	@Override
	public Integer getFirendsNum(Integer uid) throws Exception {
		XmppManager manage = getXmppManagerForPool(uid);
		return manage.getFirendsNum();
	}


	/**
	 * 判断bluId是否可以修改,目前业务场景，修改过一次，即userNum字段有值，则不能再修改
	 */
	@Override
	public Boolean canModifyUserNum(Integer uid) {
		// TODO Auto-generated method stub

		Boolean isCanModify = false;

		try {
			DxUserModel userModel = mapper.selectByPrimaryKey(uid);

			isCanModify = StringUtils.isEmpty(userModel.getUserNum()) ? true : false;

		} catch (Exception e) {
			logger.error("{} canModifyUserNum is error", uid, e);
		}

		return isCanModify;
	}

	@Override
	public Boolean modifyUserInfo(DxUserModel userModel) {

		Boolean modifyResult = false;

		Integer updateResult;
		try {
			updateResult = mapper.updateByPrimaryKey(userModel);
			modifyResult = updateResult > 0 ? true : false;

			if (modifyResult) {
				modifyUserInfoCompletenes(userModel.getUid());
			}

		} catch (Exception e) {
			logger.error("{} modifyUserInfo  is error", userModel, e);
		}

		return modifyResult;
	}

	@Override
	public Boolean modifyUserInfoCompletenes(Integer uid) {

		Boolean modifyResult = false;
		Integer updateResult;

		try {
			DxUserModel userModel = mapper.selectByPrimaryKey(uid);

			Integer completeDegree = getUserInfoCompletenes(userModel);

			DxUserModel userComplete = new DxUserModel();

			userComplete.setCompleteDegree(completeDegree);

			updateResult = mapper.updateByPrimaryKey(userModel);

			modifyResult = updateResult > 0 ? true : false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return modifyResult;
	}

	private Integer getUserInfoCompletenes(DxUserModel userModel) {

		Integer completeDegree = 40;

		List<Integer> fillin = new ArrayList<Integer>();

		fillin.add(StringUtils.isEmpty(userModel.getUserName()) ? null : 1);
		fillin.add(1);// 电话
		fillin.add(1);// 性别
		fillin.add(1);// 恋爱倾向
		fillin.add(StringUtils.isEmpty(userModel.getSign()) ? null : 1);
		fillin.add(StringUtils.isEmpty(userModel.getBirthday()) ? null : 1);
		fillin.add(StringUtils.isEmpty(userModel.getConstellation()) ? null : 1);
		fillin.add(StringUtils.isEmpty(userModel.getJobClassC()) ? null : 1);
		fillin.add(StringUtils.isEmpty(userModel.getSchoolClassC()) ? null : 1);

		completeDegree = fillin.size() / 10 * 100;

		return completeDegree;
	}

	@Override
	public Boolean setBirthdayReminderList(Integer uid, String fUid, Boolean isSet) {

		boolean setResult = false;
		try {
			XmppManager xmppManager = getXmppManagerForPool(uid);

			if (isSet) {
				setResult = xmppManager.addBirthdayReminderList(uid, fUid);
			} else {
				setResult = xmppManager.removeBirthdayReminderList(uid, fUid);
			}

		} catch (Exception e) {
			logger.error("setBirthdayReminderList {}", uid, e);
			setResult = false;
		}

		return setResult;
	}

	@Override
	public FriendsBirthdayList getFriendsBirthdayList(Integer uid) {

		TagInfo tagInfo = labelManageBiz.getMyTagFriends(uid.toString(), BIRTHDAYREMINDERLIST);

		FriendsBirthdayList friendsBirthdayList = castFriendsBirthdayList(tagInfo);

		logger.info("getFriendsBirthdayList {}", friendsBirthdayList.toString());
		return friendsBirthdayList;
	}

	private FriendsBirthdayList castFriendsBirthdayList(TagInfo tagInfo) {

		String toDay = DateUtil.getDateTime(DateUtil.BIRTHDAY_FORMAT);

		FriendsBirthdayList fBirthdayList = new FriendsBirthdayList();

		List<FriendInfo> birthdayFriends = new ArrayList<FriendInfo>();

		List<DxUserModel> dxUserModels = null;

		fBirthdayList.setUid(tagInfo.getUid());

		StringBuffer fuidsSql = new StringBuffer();
		fuidsSql.append("(");
		for (int i = 0; i <= tagInfo.getFriendsInfo().size(); i++) {
			fuidsSql.append(tagInfo.getFriendsInfo().get(i).getFrienduid().split("@")[0]);
			if (i + 1 < tagInfo.getFriendsInfo().size()) {
				fuidsSql.append(",");
			}
		}
		fuidsSql.append(")");
		String sql = "select uid,birthday from tbl_user where uid in " + fuidsSql;

		try {
			dxUserModels = mapper.selectBySql(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (DxUserModel userModel : dxUserModels) {

			FriendInfo friendInfo = new FriendInfo();

			if (userModel.getBirthday().endsWith(toDay)) {
				friendInfo.setFrienduid(friendInfo.getFrienduid());
			}

			birthdayFriends.add(friendInfo);
		}

		fBirthdayList.setFriends(birthdayFriends);

		fBirthdayList.setFnum(birthdayFriends.size());

		logger.info("castMyBlackList {}", fBirthdayList.toString());
		return fBirthdayList;
	}
}
