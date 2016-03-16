package com.dianxin.imessage.common.aspect;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.exception.BaseException;
import com.dianxin.imessage.common.extend.ExtendContext;
import com.dianxin.imessage.common.util.ResultModel;

@Aspect
@Component
public class ExceptionAspect {

	@AfterThrowing(pointcut = "execution(* com.dianxin.imessage.web..*.*(..)) || "
			+ "execution(* com.dianxin.imessage.biz..*.*(..)) || "
			+ "execution(* com.dianxin.imessage.dao..*.*(..)) || "
			+ "execution(* com.dianxin.imessage.common.aspect..*.*(..))", throwing = "e")
	public void excepctionAspect(Exception e) {

		HttpServletResponse response = ExtendContext.getResponse();
		@SuppressWarnings("rawtypes")
		ResultModel result = new ResultModel();
		result.setResult(ResultModel.RESULT_FAIL_NUM);
		if (e instanceof BaseException) {
			result.setErrCode(((BaseException) e).getErrCode());
		} else if (e instanceof DataIntegrityViolationException) {
			result.setErrCode(ErrCodeEnum.DATA_VIOLATIO_NEXCEPTION.value);
		} else {
			result.setErrCode(ErrCodeEnum.SERVICE_ERROR.value);
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(result.toJSON());

		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
