package com.dianxin.imessage.web.core.resolver;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.util.Base64Utils;
import com.dianxin.imessage.common.util.DESUtil;

/**
 * 前端参数解析器 - DES解密
 * 
 * @author b_fatty Date 2015/12/24
 */
public class DESParamResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		return parameter.getParameterAnnotation(DESParam.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		RequestMapping classRM = parameter.getMethod().getDeclaringClass().getAnnotation(RequestMapping.class);
		RequestMapping methodRM = parameter.getMethodAnnotation(RequestMapping.class);
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		String mappingPath = classRM.value()[0] + methodRM.value()[0];
		String[] mappingPathArray = mappingPath.split("\\/");
		String[] uriArray = request.getRequestURI().split("\\/");

		String[] paramNames = null;

		if (null == parameter.getParameterAnnotation(DESParam.class).value() || parameter.getParameterAnnotation(DESParam.class).value().length==0) {
			paramNames = new String[]{parameter.getParameterName()};
		} else {
			paramNames = parameter.getParameterAnnotation(DESParam.class).value();
		}

		//以restful格式提交的情况
		if(paramNames.length==1&&mappingPath.indexOf("{"+paramNames[0]+"}")>0){
			Integer index = null;
			for (int i = 0; i < mappingPathArray.length; i++) {
				if (mappingPathArray[i].equals("{" + paramNames[0] + "}")) {
					index = i;
					break;
				}
			}
			if (null != index) {
				if(parameter.getParameterType() == String.class){
					return decrypt(uriArray[index]);
				}else if(parameter.getParameterType() == String[].class){
					//restful 数组情况  前端将数组元素先加密  在用|符号串起来
					String[] param = uriArray[index].split("\\|");
					if(param != null){
						for(int i=0; i<param.length; i++ ){
							param[i] = decrypt(param[i]);
						}
					}
					return param;
				}else if(parameter.getParameterType() == Integer.class||parameter.getParameterType() == int.class){
					return Integer.parseInt(decrypt(uriArray[index]));
				}
			}
		}
		//非restful格式提交的情况
		else{
			Class<?> clazz = parameter.getParameterType();
			//对String类型的支持
			if(clazz == String.class){
				return decrypt(webRequest.getParameter(paramNames[0]));
			}
			//对String数组的支持
			else if(clazz == String[].class){
				String[] param =  webRequest.getParameterValues(paramNames[0]);
				if(param != null){
					for(int i=0; i<param.length; i++ ){
						param[i] = decrypt(param[i]);
					}
				}
				return param;
			}else if(clazz == Integer.class){
				//TODO
			}else if(clazz == Float.class){
				//TODO
			}else if(clazz == Byte.class){
				//TODO
			}else if(clazz == Long.class){
				//TODO
			}else if(clazz == Double.class){
				//TODO
			}else if(clazz == Character.class){
				//TODO
			}else if(clazz == Short.class){
				//TODO
			}else if(clazz == BigDecimal.class){
				//TODO
			}else if(clazz == BigInteger.class){
				//TODO
			}else if(clazz == Boolean.class){
				//TODO
			}else if(clazz == Date.class){
				//TODO
			}
			//对自定义类型的支持
			else{
				Object param = clazz.newInstance();   //被注解的参数对象
				Field[] fields = clazz.getDeclaredFields();	  //被注解对象的字段	
				for(Field field : fields){
					field.setAccessible(true);
					boolean isDes = false;  //该字段是否需解密
					for(String paramName : paramNames){
						//如果该字段需要解密
						if(paramName.equals(field.getName())){
							isDes = true;
							setObjectDesVal(field,webRequest,param);
						}
					}
					if(!isDes){
						setObjectVal(field,webRequest,param);
					}
				}
				return param;
			}
		}
		return null;
	}
	
	//自定义对象解密赋值
	private void setObjectDesVal(Field field,NativeWebRequest webRequest,Object param) throws Exception{
		if(field.getType()==String.class && webRequest.getParameter(field.getName())!=null){
			field.set(param, decrypt(webRequest.getParameter(field.getName())));
		}else if((field.getType() == Integer.class || field.getType() == int.class)&&webRequest.getParameter(field.getName())!=null){
			field.set(param,Integer.parseInt(decrypt(webRequest.getParameter(field.getName()))));
		}else if(field.getType() == BigDecimal.class && webRequest.getParameter(field.getName())!=null){
			field.set(param,new BigDecimal(decrypt(webRequest.getParameter(field.getName()))));
		}else if((field.getType() == Long.class || field.getType() == long.class)&&webRequest.getParameter(field.getName())!=null){
			field.set(param,Long.parseLong(decrypt(webRequest.getParameter(field.getName()))));
		}
		//TODO 更多数据类型  需要时添加
	}
	
	//自定义对象普通赋值
	private void setObjectVal(Field field,NativeWebRequest webRequest,Object param) throws Exception{
		if(field.getType()==String.class && webRequest.getParameter(field.getName())!=null){
			field.set(param, webRequest.getParameter(field.getName()));
		}else if((field.getType() == Integer.class || field.getType() == int.class)&&webRequest.getParameter(field.getName())!=null){
			field.set(param,Integer.parseInt(webRequest.getParameter(field.getName())));
		}else if(field.getType() == BigDecimal.class && webRequest.getParameter(field.getName())!=null){
			field.set(param,new BigDecimal(webRequest.getParameter(field.getName())));
		}else if((field.getType() == Long.class || field.getType() == long.class)&&webRequest.getParameter(field.getName())!=null){
			field.set(param,Long.parseLong(webRequest.getParameter(field.getName())));
		}
		//TODO 更多数据类型  需要时添加
	}
	
	//对字符进行解密
	private String decrypt(String desStr) throws Exception{
		String str = null;
		if(null != desStr){
			byte[] bytes =  Base64Utils.decode(desStr);
			str = DESUtil.decrypt(new String(bytes, "GBK"));
		}
		return str;
	}
}
