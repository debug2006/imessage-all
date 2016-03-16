package com.dianxin.imessage.web.service.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianxin.imessage.biz.login.ILoginBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.common.util.ValidatorTools;

/**
 * 登录接口 接口编码: /login <br>
 * 
 * @author kai.fantasy
 *
 */
@Controller
@RequestMapping("/login/")
public class UserLoginController {

	private Logger logger = LoggerFactory.getLogger(UserLoginController.class);

	@Autowired
	private ILoginBiz loginBiz;

	/**
	 * 用户登录
	 * 
	 * @param userName
	 *            用户名(手机号/纸条号) <br>
	 * @param password
	 *            密码 <br>
	 * 
	 * @return 登录成功返回uid，-1 用户不存在 -2 密码验证错误
	 */
	@RequestMapping(value = "userLogin/{userName}/{password}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String login(@PathVariable String userName, @DESParam("password") String password) {
		logger.debug("login start {0}");
		ResultModel<String> result = new ResultModel<String>();
		String loginResult = loginBiz.login(userName, password);
		if (ValidatorTools.isUserUid(loginResult)) {
			result.setData(loginResult);
		} else {
			result.setResult(ResultModel.RESULT_FAIL_NUM);
			result.setErrCode(loginResult);
		}

		return result.toJSON();
	}
}
