package com.dianxin.imessage.web.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.config.ISchoolBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.dataobject.config.SchoolNameList;

@Controller
@RequestMapping(value = "/config")
public class EditUserInfoController {

	@Autowired
	private IUserBiz userBiz;

	@Autowired
	private ISchoolBiz schoolBiz;

	/**
	 * 
	 * 修改个人昵称
	 * 
	 * @param uid
	 * @param userName
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@RequestMapping(value = "/modifyUserName/{uid}/{userName}", method = RequestMethod.PUT, produces = "text/html;charset=UTF-8")
	public @ResponseBody String modifyUserName(@DESParam("uid") Integer uid, @PathVariable String userName) {

		ResultModel<Boolean> result = new ResultModel<Boolean>();

		DxUserModel userModel = new DxUserModel();

		userModel.setUid(uid);
		userModel.setUserName(userName);

		Boolean modifyResult = userBiz.modifyUserInfo(userModel);

		result.setData(modifyResult);

		return result.toJSON();

	}

	/**
	 * 
	 * 判断bluId是否可以修改
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/canModifyUserNum/{uid}", method = RequestMethod.GET)
	public @ResponseBody String canModifyUserNum(@DESParam("uid") Integer uid) {

		ResultModel<Boolean> result = new ResultModel<Boolean>();

		Boolean isCanModify = userBiz.canModifyUserNum(uid);

		result.setData(isCanModify);

		return result.toJSON();
	}

	/**
	 * 
	 * 修改用户的bluId 即 userNum
	 * 
	 * @param uid
	 * @param userNum
	 * @return
	 */
	@RequestMapping(value = "/modifyUserNum/{uid}/{userNum}", method = RequestMethod.PUT, produces = "text/html;charset=UTF-8")
	public @ResponseBody String modifyUserNum(@DESParam("uid") Integer uid, @PathVariable String userNum) {
		ResultModel<Boolean> result = new ResultModel<Boolean>();

		DxUserModel userModel = new DxUserModel();

		userModel.setUid(uid);
		userModel.setUserNum(userNum);

		Boolean modifyResult = userBiz.modifyUserInfo(userModel);

		result.setData(modifyResult);

		return result.toJSON();
	}

	/**
	 * 
	 * 修改个人签名（心情）
	 * 
	 * @param uid
	 * @param sign
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@RequestMapping(value = "/modifySign/{uid}/{sign}", method = RequestMethod.PUT, produces = "text/html;charset=UTF-8")
	public @ResponseBody String modifySign(@DESParam("uid") Integer uid, @PathVariable String sign) {

		ResultModel<Boolean> result = new ResultModel<Boolean>();

		DxUserModel userModel = new DxUserModel();

		userModel.setUid(uid);
		userModel.setUserNum(sign);

		Boolean modifyResult = userBiz.modifyUserInfo(userModel);

		result.setData(modifyResult);

		return result.toJSON();
	}

	/**
	 * 
	 * 修改个人性别
	 * 
	 * @param uid
	 * @param sex
	 * @return
	 */
	@RequestMapping(value = "/modifySex/{uid}/{sex}", method = RequestMethod.PUT, produces = "text/html;charset=UTF-8")
	public @ResponseBody String modifySex(@DESParam("uid") Integer uid, @PathVariable Integer sex) {

		ResultModel<Boolean> result = new ResultModel<Boolean>();

		DxUserModel userModel = new DxUserModel();

		userModel.setUid(uid);
		userModel.setSex(sex);

		Boolean modifyResult = userBiz.modifyUserInfo(userModel);

		result.setData(modifyResult);

		return result.toJSON();
	}

	/**
	 * 
	 * 修改恋爱倾向
	 * 
	 * @param uid
	 * @param sexual
	 * @return
	 */
	@RequestMapping(value = "/modifySexual/{uid}/{sexual}", method = RequestMethod.PUT, produces = "text/html;charset=UTF-8")
	public @ResponseBody String modifySexual(@DESParam("uid") Integer uid, @PathVariable Integer sexual) {

		ResultModel<Boolean> result = new ResultModel<Boolean>();

		DxUserModel userModel = new DxUserModel();

		userModel.setUid(uid);
		userModel.setSex(sexual);

		Boolean modifyResult = userBiz.modifyUserInfo(userModel);

		result.setData(modifyResult);

		return result.toJSON();
	}

	/**
	 * 
	 * 〈修改生日、星座〉
	 * 
	 * @param uid
	 * @param birthday
	 * @param constellation
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@RequestMapping(value = "/modifyBirthday/{uid}/{birthday}/{constellation}", method = RequestMethod.PUT, produces = "text/html;charset=UTF-8")
	public @ResponseBody String modifyBirthday(@DESParam("uid") Integer uid, @PathVariable String birthday,
			@PathVariable String constellation) {

		ResultModel<Boolean> result = new ResultModel<Boolean>();

		DxUserModel userModel = new DxUserModel();

		userModel.setUid(uid);
		userModel.setBirthday(birthday);
		userModel.setConstellation(constellation);

		Boolean modifyResult = userBiz.modifyUserInfo(userModel);

		result.setData(modifyResult);

		return result.toJSON();
	}

	/**
	 * 
	 * 修改学校信息
	 * 
	 * @param uid
	 * @param schoolName
	 * @return
	 */
	@RequestMapping(value = "/modifySchool/{uid}/{schoolName}/", method = RequestMethod.PUT, produces = "text/html;charset=UTF-8")
	public @ResponseBody String modifySchool(@DESParam("uid") Integer uid, @PathVariable String schoolName) {

		ResultModel<Boolean> result = new ResultModel<Boolean>();

		DxUserModel userModel = new DxUserModel();

		userModel.setUid(uid);
		userModel.setSchoolClassC(schoolName);

		Boolean modifyResult = userBiz.modifyUserInfo(userModel);

		result.setData(modifyResult);

		return result.toJSON();
	}

	/**
	 * 
	 * 模糊查询学校信息
	 * 
	 * @param inputDesc
	 *            填写的内容
	 * @param pageId
	 *            当前的页数
	 * @param pageSize
	 *            页面展示的行数
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	@RequestMapping(value = "/querySchools/{inputDesc}/{pageId}/{pageSiz}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String querySchools(@PathVariable("inputDesc") String inputDesc,
			@PathVariable("pageId") Integer pageId, @PathVariable("pageSiz") Integer pageSize) {

		ResultModel<SchoolNameList> result = new ResultModel<SchoolNameList>();

		SchoolNameList schoolList = schoolBiz.querySchools(inputDesc, pageId, pageSize);

		result.setData(schoolList);

		return result.toJSON();
	}

}
