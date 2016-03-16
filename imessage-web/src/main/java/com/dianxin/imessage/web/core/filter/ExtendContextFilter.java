package com.dianxin.imessage.web.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dianxin.imessage.common.extend.ExtendContext;

/**
 * 
 * @author b_fatty
 * 
 * 为自定义上下文设置HttpServletRequest HttpServletResponse HttpSession
 * 
 * Date 2016/1/20
 *
 */
public class ExtendContextFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		ExtendContext.setRequest((HttpServletRequest)request);
		ExtendContext.setResponse((HttpServletResponse)response);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
