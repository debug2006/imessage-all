package com.dianxin.imessage.common.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dianxin.imessage.common.cache.Memcached;
import com.dianxin.imessage.common.cache.MemcachedFactory;
import com.dianxin.imessage.common.exception.CheckTokenException;
import com.dianxin.imessage.common.exception.ExceptionMessageResolver;
import com.dianxin.imessage.common.exception.MemcachedException;
import com.dianxin.imessage.common.extend.ExtendContext;
import com.dianxin.imessage.common.util.TokenUtil;
/**
 * 
 * @author b_fatty
 * 
 * token切面
 * date : 2016/1/20
 *
 */
@Aspect
//@Component
public class TokenAspect {

	@Autowired
	private MemcachedFactory memcachedFactory;
	
	@Autowired
	private ExceptionMessageResolver emResolver;
	
	private Logger log = LoggerFactory.getLogger(TokenAspect.class);
	
	/**
	 * @author b_fatty
	 * 
	 * 设置新的token
	 * 
	 * 范围：所有service层所有接口
	 * @throws MemcachedException 
	 * 
	 */
	@After("execution(* com.dianxin.imessage.web.service..*.*(..))")
	public void setToken() throws Exception{
		
		Memcached memcached = memcachedFactory.createMemcached();
		
		HttpServletResponse response = ExtendContext.getResponse();
		
		String token = TokenUtil.getTokenString(memcached);
		response.setHeader(TokenUtil.TOKEN_STRING_NAME, token);
		
	}
	
	/**
	 * @author b_fatty
	 * 
	 * 验证token
	 * 
	 * 范围：除login、register外所有模块的接口
	 * @throws MemcachedException 
	 */
	@Before("execution(* com.dianxin.imessage.web.service..*.*(..)) && ! "
		  + "execution(* com.dianxin.imessage.web.service.login..*.*(..)) && ! "
		  + "execution(* com.dianxin.imessage.web.service.register..*.*(..))")
	public void checkToken() throws Exception{
		
		Memcached memcached = memcachedFactory.createMemcached();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String token = request.getHeader(TokenUtil.TOKEN_STRING_NAME);
		boolean bool = TokenUtil.isTokenStringValid(token, memcached);
		if(!bool){
			throw new CheckTokenException(emResolver.getExceptionMessage(CheckTokenException.class));
		}
	}
	
}
