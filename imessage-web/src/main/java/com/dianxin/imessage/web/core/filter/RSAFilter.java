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

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dianxin.imessage.common.util.PropertiesUtil;
import com.dianxin.imessage.common.util.RSAKeyStore;
import com.dianxin.imessage.web.core.extend.RSAHttpServletRequest;

/**
 * 参数加解密过滤器
 * 
 * @author b_fatty
 * Date 2015/12/23
 *
 */
public class RSAFilter implements Filter{

	private RSAKeyStore store;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		store = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest)request)
				.getSession().getServletContext())
				.getBean(RSAKeyStore.class);
		RSAHttpServletRequest rsaRequest = new RSAHttpServletRequest((HttpServletRequest)request);
		rsaRequest.setStroe(store);
		((HttpServletResponse)response).setHeader("app-version", PropertiesUtil.getPropertiesValue(PropertiesUtil.DX_APP_PRTPERTIES_PATH, "app.version"));
		chain.doFilter(rsaRequest, response);
		
	}
	
	@Override
	public void destroy() {
		
	}

}
