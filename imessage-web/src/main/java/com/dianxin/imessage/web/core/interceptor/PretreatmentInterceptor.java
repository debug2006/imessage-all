package com.dianxin.imessage.web.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dianxin.imessage.common.util.RandomNumUtil;

/**
 * 预处理拦截器
 * 	1.设置版本号响应头信息
 * 	2.设置Token响应头信息
 * 
 * @author b_fatty
 * Date 2015/1/15
 *
 */
public class PretreatmentInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//设置版本号响应头
		//TODO  需要判断缓存中有没有，有则取缓存，无则取表   现在表未设计   2015/1/15 by b_fatty
		response.setHeader("APP-VERSION", "V1.0.0");
		
		System.out.println(request.getAttribute("type"));
		
		//设置Token响应头
		response.setHeader("UUID" , RandomNumUtil.createUUID());
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
