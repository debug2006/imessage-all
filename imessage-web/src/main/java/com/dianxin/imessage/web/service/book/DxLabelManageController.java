package com.dianxin.imessage.web.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianxin.imessage.biz.book.ILabelManageBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.dao.dataobject.book.MyTagList;
import com.dianxin.imessage.dao.dataobject.book.MyTags;
import com.dianxin.imessage.dao.dataobject.book.TagInfo;
import com.dianxin.imessage.dao.dataobject.book.TagsForFriend;

/**
 * 通讯录标签组信息
 * 
 * @author kai.fatansy
 *
 */
@Controller
@RequestMapping("/book/")
public class DxLabelManageController {

	@Autowired
	private ILabelManageBiz labelManageBiz;

	/**
	 * 设置好友的(备注)昵称
	 * 
	 * @param uid
	 *            用户UID
	 * @param friendsUid
	 *            用好友的UID
	 * @param remarkDesc
	 *            备注
	 * @return
	 */
	@RequestMapping(value = "remark/{uid}/{friendsUid}/{remarkDesc}", method = RequestMethod.PUT, produces = "text/html;charset=UTF-8")
	public @ResponseBody String remark(@DESParam("uid") String uid, @DESParam("friendsUid") String friendsUid,
			@PathVariable String remarkDesc) {

		ResultModel<String> result = new ResultModel<String>();
		String remarkResult = labelManageBiz.remark(Integer.valueOf(uid), Integer.valueOf(friendsUid), remarkDesc);
		result.setData(remarkResult);
		return result.toJSON();
	}

	/**
	 * 删除好友
	 * 
	 * @param uid
	 *            用户UID
	 * @param friendsUid
	 *            好友UID
	 * @return
	 */
	@RequestMapping(value = "friends/{uid}/{friendsId}", method = RequestMethod.DELETE, produces = "text/html;charset=UTF-8")
	public @ResponseBody String deleteFriends(@DESParam("uid") String uid, @DESParam("friendsId") String[] friendsId) {

		ResultModel<String> result = new ResultModel<String>();
		String remarkResult = labelManageBiz.deleteFriends(Integer.valueOf(uid), friendsId);
		result.setData(remarkResult);
		return result.toJSON();
	}

	/**
	 * 查询标签组信息
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "myTags/{uid}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String myTags(@DESParam("uid") String uid) {

		ResultModel<MyTags> result = new ResultModel<MyTags>();
		MyTags myTags = labelManageBiz.getMyTags(Integer.valueOf(uid));
		if (null != myTags) {
			result.setData(myTags);
		} else {
			result.setResult(ResultModel.RESULT_FAIL_NUM);
			result.setErrCode(ErrCodeEnum.SERVICE_TAG_ERROR.value);
		}
		return result.toJSON();
	}

	/**
	 * 查询标签组信息(标签名称，标签中的好友数量)
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "myTagList/{uid}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String myTagList(@DESParam("uid") String uid) {

		ResultModel<MyTagList> result = new ResultModel<MyTagList>();
		MyTagList myTags = labelManageBiz.getMyTagList(uid);
		if (null != myTags) {
			result.setData(myTags);
		} else {
			result.setResult(ResultModel.RESULT_FAIL_NUM);
			result.setErrCode(ErrCodeEnum.SERVICE_TAG_ERROR.value);
		}
		return result.toJSON();
	}

	/**
	 * 查询标签组信息(标签名称，标签中的好友数量)
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "myTagFriends/{uid}/{tagName}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String myTagFriends(@DESParam("uid") String uid, @PathVariable String tagName) {

		ResultModel<TagInfo> result = new ResultModel<TagInfo>();
		TagInfo myTags = labelManageBiz.getMyTagFriends(uid, tagName);
		if (null != myTags) {
			result.setData(myTags);
		} else {
			result.setResult(ResultModel.RESULT_FAIL_NUM);
			result.setErrCode(ErrCodeEnum.SERVICE_TAG_ERROR.value);
		}
		return result.toJSON();
	}

	/**
	 * 新增标签组信息
	 * 
	 * @param tagInfo
	 * @return
	 */
	@RequestMapping(value = "myTags", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody String addMyTags(@RequestBody TagInfo tagInfo) {

		ResultModel<String> result = new ResultModel<String>();
		String createResult = labelManageBiz.createTagInfo(tagInfo);
		result.setData(createResult);
		return result.toJSON();
	}

	/**
	 * 更新标签组信息
	 * 
	 * @param tagInfo
	 * @return
	 */
	@RequestMapping(value = "updateMyTags", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody String updateMyTags(@RequestBody TagInfo tagInfo) {

		ResultModel<String> result = new ResultModel<String>();
		String createResult = labelManageBiz.updateTagInfo(tagInfo);
		result.setData(createResult);
		return result.toJSON();
	}

	/**
	 * 移除标签组信息
	 * 
	 * @param tagInfo
	 * @return
	 */
	@RequestMapping(value = "tags/{uid}/{tagName}", method = RequestMethod.DELETE, produces = "text/html;charset=UTF-8")
	public @ResponseBody String removeMyTags(@DESParam("uid") String uid, @PathVariable String tagName) {

		ResultModel<String> result = new ResultModel<String>();
		String createResult = labelManageBiz.removeTagInfo(Integer.valueOf(uid), tagName);
		result.setData(createResult);
		return result.toJSON();
	}

	@RequestMapping(value = "setTagsForFriend", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody String setTagsForFriend(@RequestBody TagsForFriend tagsForFriend) {

		ResultModel<String> result = new ResultModel<String>();

		String setResult = labelManageBiz.setTagsForFriend(tagsForFriend);

		result.setData(setResult);

		return result.toJSON();

	}

}
