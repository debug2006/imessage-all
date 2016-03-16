package com.dianxin.imessage.common.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.dianxin.imessage.common.constant.ErrCodeEnum;

public class ExceptionMessageResolver {

	@Autowired
	ResourceBundleMessageSource exceptionMessageSource;
	
	public String getExceptionMessage(Class<? extends Exception> clazz){
		String message = null;
		if(clazz == CheckTokenException.class){
			message = exceptionMessageSource.getMessage(
					"token.check.message", 
					new String[]{ErrCodeEnum.TOKEN_CHECK_FAIL.value},
					Locale.getDefault());
		}
		else if(clazz == MemcachedException.class){
			message = exceptionMessageSource.getMessage(
					"memcache.err", 
					new String[]{ErrCodeEnum.MEMCACHED_ERR.value},
					Locale.getDefault());
		}
		return message;
	} 
}
