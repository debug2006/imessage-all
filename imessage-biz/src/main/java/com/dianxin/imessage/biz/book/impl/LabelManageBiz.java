package com.dianxin.imessage.biz.book.impl;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.book.ILabelManageBiz;
import com.dianxin.imessage.common.xmpp.XmppManager;
import com.dianxin.imessage.dao.dataobject.book.FriendInfo;
import com.dianxin.imessage.dao.dataobject.book.MyTagList;
import com.dianxin.imessage.dao.dataobject.book.MyTags;
import com.dianxin.imessage.dao.dataobject.book.Tag;
import com.dianxin.imessage.dao.dataobject.book.TagInfo;
import com.dianxin.imessage.dao.dataobject.book.TagsForFriend;

/**
 * 
 * 〈标签组功能实现〉
 * 
 * @author kai.fantasy
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class LabelManageBiz implements ILabelManageBiz {

	private static Logger logger = LoggerFactory.getLogger(LabelManageBiz.class);

	@Autowired
	private IUserBiz userBiz;

	/**
	 * 设置好友昵称
	 * 
	 * @param uid
	 * @param friendsUid
	 * @param remarkDesc
	 * @return 0 ：失败 1：成功
	 */
	@Override
	public String remark(Integer uid, Integer friendsUid, String remarkDesc) {

		try {
			XmppManager xmppManager = userBiz.getXmppManagerForPool(uid);

			xmppManager.setEntryNiceName(friendsUid, remarkDesc);

		} catch (Exception e) {
			logger.error("LabelManageBiz.remark", e);
			return "0";
		}

		return "1";
	}

	/**
	 * 删除好友列表
	 * 
	 * @param uid
	 * @param friendsId
	 * @return 0 ：失败 1：成功
	 */
	@Override
	public String deleteFriends(Integer uid, String[] friendsId) {

		try {
			XmppManager xmppManager = userBiz.getXmppManagerForPool(uid);

			xmppManager.removeUser(friendsId);

		} catch (Exception e) {
			logger.error("LabelManageBiz.deleteFriends", e);
			return "0";
		}

		return "1";
	}

	/**
	 * 根据UID 获取标签信息
	 * 
	 * @param uid
	 */
	@Override
	public MyTags getMyTags(Integer uid) {

		MyTags myTags = new MyTags();

		List<TagInfo> tagInfos = new ArrayList<TagInfo>();
		try {
			XmppManager xmppManager = userBiz.getXmppManagerForPool(uid);

			// 获取标签组
			List<RosterGroup> rosterGroups = xmppManager.getGroups();

			// 标签组信息
			for (RosterGroup rosterGroup : rosterGroups) {
				TagInfo tagInfo = new TagInfo();
				tagInfo.setTagName(rosterGroup.getName());
				List<FriendInfo> friendsInfo = getGroupMembers(xmppManager, rosterGroup.getName());
				tagInfo.setFriendsInfo(friendsInfo);
				tagInfo.setUid(uid);
				tagInfos.add(tagInfo);
			}

			myTags.setUid(uid);
			myTags.setTags(tagInfos);

		} catch (Exception e) {
			logger.error("LabelManageBiz.getMyTags", e);
			return null;
		}
		return myTags;
	}

	/**
	 * 获取标签组好友
	 * 
	 * @param xmppManager
	 * 
	 * @param roster
	 * @param groupName
	 * @return
	 */
	private List<FriendInfo> getGroupMembers(XmppManager xmppManager, String groupName) {

		List<FriendInfo> groupMembers = new ArrayList<FriendInfo>();

		List<RosterEntry> rosterEntrys = xmppManager.getEntriesByGroup(groupName);

		for (RosterEntry rosterEntry : rosterEntrys) {
			FriendInfo friendInfo = new FriendInfo();
			friendInfo.setFrienduid(rosterEntry.getUser());
			friendInfo.setRemarkDesc(rosterEntry.getName());
			groupMembers.add(friendInfo);
		}

		return groupMembers;
	}

	@Override
	public String createTagInfo(TagInfo tagInfo) {

		try {
			XmppManager xmppManager = userBiz.getXmppManagerForPool(tagInfo.getUid());

			// 设置好友备注
			setFriendsRemark(tagInfo);

			List<String> friendsUid = getFriendsUid(tagInfo.getFriendsInfo());

			xmppManager.addGroup(friendsUid, tagInfo.getTagName());

		} catch (Exception e) {
			logger.error("LabelManageBiz.createTagInfo", e);
			return "0";
		}

		return "1";
	}

	/**
	 * 
	 * 〈设置好友的备注〉
	 * 
	 * @param tagInfo
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	private void setFriendsRemark(TagInfo tagInfo) {

		for (FriendInfo friendInfo : tagInfo.getFriendsInfo()) {
			this.remark(tagInfo.getUid(), Integer.valueOf(friendInfo.getFrienduid()), friendInfo.getRemarkDesc());
		}

	}

	/**
	 * 获取好友UID列表
	 * 
	 * @param friendsInfo
	 * @return
	 */
	private List<String> getFriendsUid(List<FriendInfo> friendsInfo) {

		List<String> friendsUid = new ArrayList<String>();

		for (FriendInfo friendInfo : friendsInfo) {
			friendsUid.add(friendInfo.getFrienduid());
		}

		return friendsUid;
	}

	/**
	 * 更新标签组信息
	 * 
	 * @param tagInfo
	 */
	@Override
	public String updateTagInfo(TagInfo tagInfo) {

		try {

			// this.removeTagInfo(tagInfo.getUid(), tagInfo.getOldTagName());

			// 设置好友备注
			setFriendsRemark(tagInfo);

			// this.createTagInfo(tagInfo);

			XmppManager xmppManager = userBiz.getXmppManagerForPool(tagInfo.getUid());

			List<String> friendsUid = getFriendsUid(tagInfo.getFriendsInfo());

			xmppManager.updateGroup(friendsUid, tagInfo.getTagName(), tagInfo.getOldTagName());

		} catch (Exception e) {
			logger.error("LabelManageBiz.updateTagInfo", e);
			return "0";
		}

		return "1";
	}

	/**
	 * 删除标签组信息
	 * 
	 * @param uid
	 * @param tagName
	 * @return "0" : 删除失败 "1" : 删除成功
	 */
	@Override
	public String removeTagInfo(Integer uid, String tagName) {

		try {
			XmppManager xmppManager = userBiz.getXmppManagerForPool(uid);
			xmppManager.removeGroup(tagName);

		} catch (Exception e) {
			logger.error("LabelManageBiz.removeTagInfo", e);
			return "0";
		}

		return "1";
	}

	/**
	 * 获取标签信息 （标签名称，好友数量）
	 */
	@Override
	public MyTagList getMyTagList(String uid) {

		MyTagList tagList = new MyTagList();

		List<Tag> tagInfos = new ArrayList<Tag>();
		try {
			XmppManager xmppManager = userBiz.getXmppManagerForPool(Integer.valueOf(uid));

			// 获取标签组
			List<RosterGroup> rosterGroups = xmppManager.getGroups();

			// 标签组信息
			for (RosterGroup rosterGroup : rosterGroups) {
				Tag tag = new Tag();
				tag.setTagName(rosterGroup.getName());
				List<FriendInfo> friendsInfo = getGroupMembers(xmppManager, rosterGroup.getName());
				tag.setFriendNum(friendsInfo.size());
				tagInfos.add(tag);
			}

			tagList.setUid(uid);
			tagList.setTagNum(tagInfos.size());
			tagList.setTags(tagInfos);

		} catch (Exception e) {
			logger.error("LabelManageBiz.getMyTagList", e);
			return null;
		}
		return tagList;

	}

	@Override
	public TagInfo getMyTagFriends(String uid, String tagName) {

		TagInfo tagInfo = new TagInfo();

		try {
			XmppManager xmppManager = userBiz.getXmppManagerForPool(Integer.valueOf(uid));

			tagInfo.setTagName(tagName);
			List<FriendInfo> friendsInfo = getGroupMembers(xmppManager, tagName);
			tagInfo.setFriendsInfo(friendsInfo);
			tagInfo.setUid(Integer.valueOf(uid));

		} catch (Exception e) {
			logger.error("LabelManageBiz.getMyTagFriends", e);
			return null;
		}

		return tagInfo;
	}

	/**
	 * 设置好友到分组列表中
	 */
	@Override
	public String setTagsForFriend(TagsForFriend tagsForFriend) {

		// 设置好友备注
		// setFriendsRemark(tagInfo);
		TagInfo tagInfo = null;
		for (String tagName : tagsForFriend.getTagNames()) {

			tagInfo = new TagInfo();
			tagInfo.setUid(tagsForFriend.getUid());
			List<FriendInfo> friendInfos = new ArrayList<FriendInfo>();
			friendInfos.add(tagsForFriend.getFriendInfo());
			tagInfo.setFriendsInfo(friendInfos);
			tagInfo.setTagName(tagName);
			createTagInfo(tagInfo);

		}

		return "1";
	}

}
