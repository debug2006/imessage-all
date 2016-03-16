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

import com.dianxin.imessage.common.util.PropertiesUtil;
import com.dianxin.imessage.web.core.extend.DESHttpServletRequest;

public class DESFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		DESHttpServletRequest desRequest = new DESHttpServletRequest((HttpServletRequest)request);
		chain.doFilter(desRequest, response);
	}

	@Override
	public void destroy() {
		
	}

}
