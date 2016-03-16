package com.dianxin.imessage.web.service.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.book.IInterestBiz;
import com.dianxin.imessage.biz.book.IReportBiz;
import com.dianxin.imessage.biz.config.IAbilityBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.dao.dataobject.DxAbilityModel;
import com.dianxin.imessage.dao.dataobject.DxReportModel;
import com.dianxin.imessage.dao.dataobject.book.FriendsBirthdayList;
import com.dianxin.imessage.dao.dataobject.book.UserInterestList;
import com.dianxin.imessage.dao.dataobject.config.UserAbilityList;

/**
 * 好友详情
 * 
 * @author kai.fantasy
 *
 */
@Controller
@RequestMapping("/book/")
public class DxFriendsDetailsController {

	@Autowired
	private IReportBiz reportBiz;

	@Autowired
	private IAbilityBiz abilityBiz;

	@Autowired
	private IInterestBiz interestBiz;

	@Autowired
	private IUserBiz userBiz;

	/**
	 * 
	 * 〈举报〉 〈功能详细描述〉
	 * 
	 * @param reportModel
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@RequestMapping(value = "report", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String report(@DESParam({ "uid", "beUid" }) DxReportModel reportModel) {

		ResultModel<Boolean> result = new ResultModel<Boolean>();
		Boolean reportResult = reportBiz.addReportInfo(reportModel);
		result.setData(reportResult);
		return result.toJSON();
	}

	/**
	 * 根据uid获取超能力信息列表
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "abilityList/{uid}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String allAbility(@DESParam Integer uid) {

		ResultModel<UserAbilityList> result = new ResultModel<UserAbilityList>();
		UserAbilityList userAbilityList = abilityBiz.getSuperPowerList(uid);
		result.setData(userAbilityList);
		return result.toJSON();
	}

	/**
	 * 返回所有的超能力信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "abilityList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String allAbility() throws Exception {

		ResultModel<Map<String, Object>[]> result = new ResultModel<Map<String, Object>[]>();
		List<DxAbilityModel> list = abilityBiz.getDxAbilityModelList(null);
		Map<String, Object>[] maps = new Map[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("abilityId", list.get(i).getId());
			map.put("abilityName", list.get(i).getAbilityName());
			map.put("abilityDesc", list.get(i).getAbilityDesc());
			maps[i] = map;
		}
		result.setData(maps);
		return result.toJSON();
	}

	/**
	 * 获取用户兴趣列表
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "interest/{uid}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String interestList(@DESParam("uid") String uid) {
		ResultModel<UserInterestList> result = new ResultModel<UserInterestList>();
		UserInterestList userInterestList = interestBiz.getInterestList(Integer.valueOf(uid));
		result.setData(userInterestList);
		return result.toJSON();
	}

	/**
	 * 设置好友生日提醒
	 * 
	 * @param uid
	 * @param fuid
	 * @return
	 */
	@RequestMapping(value = "birthdayReminder", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String setBirthdayReminderList(@DESParam("uid") String uid, @DESParam("fUid") String fUid,
			Boolean isSet) {

		ResultModel<Boolean> result = new ResultModel<Boolean>();
		Boolean addResult = userBiz.setBirthdayReminderList(Integer.valueOf(uid), fUid, isSet);
		if (addResult) {
			result.setData(addResult);
		} else {
			result.setResult(ResultModel.RESULT_FAIL_NUM);
			result.setErrCode(ErrCodeEnum.USER_NOT_EXIST.value);

		}
		return result.toJSON();
	}

	/**
	 * 获取好友生日列表
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "birthdayReminder/{uid}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getBirthdayFriends(@DESParam("uid") String uid) {

		ResultModel<FriendsBirthdayList> result = new ResultModel<FriendsBirthdayList>();

		FriendsBirthdayList userInterestList = userBiz.getFriendsBirthdayList(Integer.valueOf(uid));

		result.setData(userInterestList);

		return result.toJSON();
	}

}
