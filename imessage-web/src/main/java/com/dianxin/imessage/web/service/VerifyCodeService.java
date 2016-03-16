package com.dianxin.imessage.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianxin.imessage.biz.IVerifyCodeBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.util.ResultModel;

/**
 * 验证码控制器
 * 
 * @author b_fatty
 *
 */
@Controller
@RequestMapping("/verifyCode")
public class VerifyCodeService {

	@Autowired
	IVerifyCodeBiz verifyCodeBiz;
	
	/**
	 * 修改密码获取验证码
	 */
	@RequestMapping(value="/modPWDVerifyCode/{phone}",produces="text/html;charset=UTF-8", method=RequestMethod.GET)
	public @ResponseBody String modPWDVerifyCode(@DESParam String phone) throws Exception{
		ResultModel result = new ResultModel();
		boolean bool = verifyCodeBiz.getModPWDVerifyCode(phone);
		if(bool)
			return result.toJSON();
		result.setResult(ResultModel.RESULT_FAIL_NUM);
		result.setErrCode(ErrCodeEnum.SMS_SEND_ERR.value);
		return result.toJSON();
	}
	
}
