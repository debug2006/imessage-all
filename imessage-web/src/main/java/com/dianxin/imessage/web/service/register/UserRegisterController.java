package com.dianxin.imessage.web.service.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianxin.imessage.biz.IVerifyCodeBiz;
import com.dianxin.imessage.biz.register.IRegisterBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.constant.UserManagerEnum;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.dao.dataobject.RegistrationFormModel;

/**
 * 
 * 〈注册〉 〈检查手机号码是否存在〉 〈用户注册〉 〈注册获取验证码〉 〈验证码校验〉
 * 
 * @author kai.fantasy
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/register/")
public class UserRegisterController {

	private Logger logger = LoggerFactory.getLogger(UserRegisterController.class);

	@Autowired
	private IRegisterBiz registerBiz;

	@Autowired
	IVerifyCodeBiz verifyCodeBiz;

	/**
	 * 检查手机号码是否已经注册
	 * 
	 * @param phone
	 *            手机号码
	 * @return 0 ：未注册 1：已注册
	 */
	@RequestMapping(value = "exitPhone/{phone}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String existPhone(@DESParam("phone") String phone) {
		logger.debug("existPhone start {}", phone);

		ResultModel<String> result = new ResultModel<String>();
		String exits = registerBiz.existPhone(phone);
		if (UserManagerEnum.PHONE_REGISTERED.value.equals(exits)
				|| UserManagerEnum.PHONE_NOT_REGISTERED.value.equals(exits)) {
			result.setData(exits);
		} else {
			result.setResult(ResultModel.RESULT_FAIL_NUM);
			result.setErrCode(exits);
		}
		return result.toJSON();
	}

	/**
	 * 用户注册
	 * 
	 * @param DxUserModel
	 * 
	 * @return 1 注册成功;-1:用户名存在;手机号已存在返回-2 持久化异常返回错误代码
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String register(
			@DESParam({ "telphone", "password" }) RegistrationFormModel registrationFormModel) {
		logger.debug("register start");
		ResultModel<String> result = new ResultModel<String>();
		String resisterResult = registerBiz.register(registrationFormModel);
		if (!ErrCodeEnum.REGISTRATION_FAILED.value.equals(resisterResult)) {
			result.setData(resisterResult);
		} else {
			result.setResult(ResultModel.RESULT_FAIL_NUM);
			result.setErrCode(resisterResult);
		}
		return result.toJSON();
	}

	/**
	 * 注册获取验证码
	 */
	@RequestMapping(value = "registerVerifyCode/{phone}", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String registerVerifyCode(@DESParam String phone) throws Exception {
		ResultModel result = new ResultModel();
		boolean bool = verifyCodeBiz.getRegisterVerifyCode(phone);
		if (bool)
			return result.toJSON();
		result.setResult(ResultModel.RESULT_FAIL_NUM);
		result.setErrCode(ErrCodeEnum.SMS_SEND_ERR.value);
		return result.toJSON();
	}

	/**
	 * 验证验证码
	 */
	@RequestMapping(value = "checkVerifyCode/{phone}/{verifyCode}", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody String checkVerifyCode(@DESParam String phone, @DESParam String verifyCode) throws Exception {
		ResultModel result = new ResultModel();
		boolean bool = verifyCodeBiz.checkVerifyCode(phone, verifyCode);
		if (bool)
			return result.toJSON();
		result.setResult(ResultModel.RESULT_FAIL_NUM);
		result.setErrCode(ErrCodeEnum.VERIFY_CODE_FAIL.value);
		return result.toJSON();
	}
}
