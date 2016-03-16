package com.dianxin.imessage.biz.book.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.book.IBlacklistBiz;
import com.dianxin.imessage.biz.book.ILabelManageBiz;
import com.dianxin.imessage.common.xmpp.XmppManager;
import com.dianxin.imessage.dao.dataobject.book.MyBlackList;
import com.dianxin.imessage.dao.dataobject.book.TagInfo;

/**
 * 黑名单
 * 
 * @author kai.fantasy
 *
 */
@Service
public class BlacklistBiz implements IBlacklistBiz {

	private static Logger logger = LoggerFactory.getLogger(LabelManageBiz.class);

	@Autowired
	private IUserBiz userBiz;

	@Autowired
	private ILabelManageBiz labelManageBiz;

	// TODO 从配置中获取
	public static String BLACKLIST_NAME = "小黑屋";

	@Override
	public Boolean setBlacklist(Integer uid, String blackUid, Boolean isSet) {

		boolean addResult = false;
		try {
			XmppManager xmppManager = userBiz.getXmppManagerForPool(uid);

			if (isSet) {
				addResult = xmppManager.addBlacklist(uid, blackUid);
			} else {
				addResult = xmppManager.removeBlacklist(uid, blackUid);
			}

		} catch (Exception e) {
			logger.error("addBlacklist {}", uid, e);
			addResult = false;
		}

		return addResult;
	}

	@Override
	public MyBlackList getBlackList(String uid) {

		TagInfo tagInfo = labelManageBiz.getMyTagFriends(uid, BLACKLIST_NAME);

		MyBlackList myBlackList = castMyBlackList(tagInfo);

		logger.info("getBlackList {}", myBlackList.toString());
		return myBlackList;
	}

	/**
	 * 
	 * 功能描述: 根据标签信息转换为黑名单列表<br>
	 * 根据标签信息转换为黑名单列表
	 *
	 * @param tagInfo
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private MyBlackList castMyBlackList(TagInfo tagInfo) {

		MyBlackList myBlackList = new MyBlackList();

		myBlackList.setUid(tagInfo.getUid());

		myBlackList.setBlacklist(tagInfo.getFriendsInfo());

		myBlackList.setBnum(tagInfo.getFriendsInfo().size());

		logger.info("castMyBlackList {}", myBlackList.toString());
		return myBlackList;
	}

	@Override
	public String updateBlackList(MyBlackList myBlackList) {

		TagInfo tagInfo = castTagInfo(myBlackList);

		String createResult = labelManageBiz.updateTagInfo(tagInfo);

		logger.info("updateBlackList result : {}", createResult);
		return createResult;
	}

	/**
	 * 
	 * 功能描述: 转换标签信息<br>
	 * 黑名单转换标签组信息
	 *
	 * @param myBlackList
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private TagInfo castTagInfo(MyBlackList myBlackList) {
		TagInfo tagInfo = new TagInfo();

		tagInfo.setFriendsInfo(myBlackList.getBlacklist());

		tagInfo.setOldTagName(BLACKLIST_NAME);

		tagInfo.setTagName(BLACKLIST_NAME);

		tagInfo.setUid(myBlackList.getUid());

		logger.info("castTagInfo : {}", tagInfo.toString());
		return tagInfo;
	}

}
