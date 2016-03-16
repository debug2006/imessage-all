package com.dianxin.imessage.web.core.extend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

import com.dianxin.imessage.common.util.DESUtil;
/**
 * DES 参数解密
 * 
 * @author b_fatty
 * Date 2015/12/30
 *
 */
public class DESHttpServletRequest extends HttpServletRequestWrapper{

	public DESHttpServletRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		if(StringUtils.isNotEmpty(super.getParameter(name))){
			DESUtil.decrypt(super.getParameter(name).replace(" ", "+"));
		}
		return null;
	}
	
	@Override
	public Map<String,String[]> getParameterMap() {
		@SuppressWarnings("unchecked")
		Map<String,String[]> supperMap = super.getParameterMap();
		Map<String,String[]> map = new HashMap<String, String[]>();
		for(Map.Entry<String, String[]> e : supperMap.entrySet()){
			if(null!=e.getValue()&&e.getValue().length>0){
				String[] s = new String[e.getValue().length];
				for(int i=0;i<s.length;i++){
					s[i] = DESUtil.decrypt(e.getValue()[i].replace(" ", "+"));
				}
				map.put(e.getKey(), s);
			}
		}
		return map;
	}
	@Override
	public String[] getParameterValues(String name) {
		String[] supperValues = super.getParameterValues(name);
		String[] values = null;
		if(null!=supperValues&&supperValues.length>0){
			values = new String[supperValues.length];
			for(int i=0;i<values.length;i++){
				values[i] = DESUtil.decrypt(supperValues[i].replace(" ", "+"));
			}
		}
		return values;
	}
}
