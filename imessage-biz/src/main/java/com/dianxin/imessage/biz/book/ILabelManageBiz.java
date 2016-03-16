package com.dianxin.imessage.biz.book;

import com.dianxin.imessage.dao.dataobject.book.MyTagList;
import com.dianxin.imessage.dao.dataobject.book.MyTags;
import com.dianxin.imessage.dao.dataobject.book.TagInfo;
import com.dianxin.imessage.dao.dataobject.book.TagsForFriend;

/**
 * 
 * <br>
 * <b>功能：</b>通讯录模块接口<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> 20160126 <br>
 */
public interface ILabelManageBiz {

	/**
	 * 
	 * 〈修改好友备注〉 〈修改好友的昵称〉
	 * 
	 * @param uid
	 * @param friendsUid
	 * @param remarkDesc
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	String remark(Integer uid, Integer friendsUid, String remarkDesc);

	/**
	 * 
	 * 〈删除好友〉 〈根据好友UID删除好友〉
	 * 
	 * @param uid
	 * @param friendsId
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	String deleteFriends(Integer uid, String[] friendsId);

	/**
	 * 
	 * 获取标签组信息
	 * 
	 * @param uid
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	MyTags getMyTags(Integer uid);

	/**
	 * 
	 * 获取标签信息 （标签名称，好友数量）
	 * 
	 * @param uid
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	MyTagList getMyTagList(String uid);

	/**
	 * 
	 * 获取标签组的中的好友
	 * 
	 * @param uid
	 * @param tagName
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	TagInfo getMyTagFriends(String uid, String tagName);

	/**
	 * 
	 * 创建标签组
	 * 
	 * @param tagInfo
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	String createTagInfo(TagInfo tagInfo);

	/**
	 * 
	 * 更新标签组
	 * 
	 * @param tagInfo
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	String updateTagInfo(TagInfo tagInfo);

	/**
	 * 
	 * 删除标签组
	 * 
	 * @param uid
	 * @param tagName
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	String removeTagInfo(Integer uid, String tagName);

	/**
	 * 
	 * 设置好友到我的分组列表中
	 * 
	 * @param tagsForFriend
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	String setTagsForFriend(TagsForFriend tagsForFriend);

}
